package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsProperties.Role
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhotos
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@RunWith(AndroidJUnit4::class)
class ImageContentScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var imageViewModel: ImageViewModel


    private val photo1 = FlickrPhoto("photo1", "owner1", "secret1", "server", 1, "title1", 1, 1,1, "owner1")
    private val photo2 = FlickrPhoto("photo2", "owner2", "secret2", "server", 2, "title2", 1, 1,1, "owner2")
    private val photo3 = FlickrPhoto("photo3", "owner3", "secret3", "server", 1, "title3", 1, 1,1, "owner3")
    private val photos = FlickrPhotos(1,1, 3, 3, listOf(photo1, photo2, photo3))
    private val images = MutableStateFlow(FlickrResponse(photos, stat = FlickrStatus.ok, 200))


    @Test
    fun givenPhotoInformation_whenShowImages_thenAssertOps():Unit = runBlocking{
        //given
       val mutableText = mutableStateOf("")
        imageViewModel = mock()

        imageViewModel.stub {
            onBlocking { fetchedPhotoInfos } doReturn images
            onBlocking { searchQuery } doReturn mutableText
        }

        composeTestRule.setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            ImageGalleryVisualisation(PaddingValues(0.dp), imageViewModel, snackbarHostState)
        }

        //when and then
        val searchBar = composeTestRule.onNode(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Search))
        searchBar.assertIsDisplayed()
        searchBar.assertIsEnabled()
        searchBar.assertIsNotFocused()

        val searchIcon = composeTestRule.onNodeWithContentDescription("search icon")
        searchIcon.assertIsEnabled()
        searchIcon.assertIsDisplayed()
        searchIcon.assertIsNotFocused()
        searchIcon.assert(SemanticsMatcher.expectValue(Role, Button))

        val closeButton  = composeTestRule.onNodeWithContentDescription("CloseButton")
        closeButton.assert(SemanticsMatcher.expectValue(Role, Button))
        closeButton.assertIsEnabled()
        closeButton.assertIsDisplayed()
        closeButton.assertIsNotFocused()

        val closeIcon  = composeTestRule.onNodeWithContentDescription("close icon")
        closeIcon.assert(SemanticsMatcher.expectValue(Role, Button))
        closeIcon.assertIsEnabled()
        closeIcon.assertIsDisplayed()
        closeIcon.assertIsNotFocused()

        val searchText  = composeTestRule.onNodeWithText("Search images…")
        searchText.assertIsEnabled()
        searchText.assertIsDisplayed()
        searchText.assertIsNotFocused()

        assertPhotos(composeTestRule, photo1)
        composeTestRule.onRoot(true).performTouchInput { swipeUp() }

        assertPhotos(composeTestRule, photo2)
        assertPhotos(composeTestRule, photo3)

    }

    private fun assertPhotos(composeTestRule: ComposeContentTestRule, photo: FlickrPhoto) {
        val title = composeTestRule.onNodeWithText(photo.title)
        title.assertIsEnabled()
        title.assertIsDisplayed()

        val authorName = composeTestRule.onNodeWithText(photo.ownername)
        authorName.assertIsEnabled()
        authorName.assertIsDisplayed()
    }
}