@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhotos
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.TestTags.PHOTO_LIST_TAG
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageContentScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val useCase = mockk<UseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val imageViewModel = ImageViewModel(useCase, dispatcher)


    private val photo1 = FlickrPhoto("photo1", "owner1", "secret1", "server", 1, "title1", 1, 1, 1, "owner1")
    private val photo2 = FlickrPhoto("photo2", "owner2", "secret2", "server", 2, "title2", 1, 1, 1, "owner2")
    private val photo3 = FlickrPhoto("photo3", "owner3", "secret3", "server", 1, "title3", 1, 1, 1, "owner3")
    private val photos = FlickrPhotos(1, 1, 3, 3, listOf(photo1, photo2, photo3))
    private val images = FlickrResponse(photos, stat = FlickrStatus.ok, 200)


    @Test
    fun givenPhotoInformation_whenShowImages_thenAssertOps(): Unit = runTest(dispatcher) {
        //given
        coEvery { useCase.getRecentImagesUseCase() } returns images

        composeTestRule.setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            ImageGalleryVisualisation(PaddingValues(0.dp), imageViewModel, snackbarHostState)
        }

        imageViewModel.getRecentPhotos()
        advanceUntilIdle()

        //when and then
        val searchBar =
            composeTestRule.onNode(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Search))
        searchBar.assertIsDisplayed()
        searchBar.assertIsEnabled()
        searchBar.assertIsNotFocused()

        val searchIcon = composeTestRule.onNodeWithContentDescription("search icon")
        searchIcon.assertIsEnabled()
        searchIcon.assertIsDisplayed()
        searchIcon.assertIsNotFocused()
        searchIcon.assert(SemanticsMatcher.expectValue(Role, Button))

        val closeButton = composeTestRule.onNodeWithContentDescription("CloseButton")
        closeButton.assert(SemanticsMatcher.expectValue(Role, Button))
        closeButton.assertIsEnabled()
        closeButton.assertIsDisplayed()
        closeButton.assertIsNotFocused()

        val closeIcon = composeTestRule.onNodeWithContentDescription("close icon")
        closeIcon.assert(SemanticsMatcher.expectValue(Role, Button))
        closeIcon.assertIsEnabled()
        closeIcon.assertIsDisplayed()
        closeIcon.assertIsNotFocused()

        val searchText = composeTestRule.onNodeWithText("Search images…")
        searchText.assertIsEnabled()
        searchText.assertIsDisplayed()
        searchText.assertIsNotFocused()

        assertPhotos(composeTestRule, photo1)
        composeTestRule.onNodeWithTag(PHOTO_LIST_TAG).performTouchInput { swipeUp() }

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