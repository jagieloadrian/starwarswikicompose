package com.anjo.starwarswikicompose.presentation.screens.specie.detail

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.UnitName.CM
import com.anjo.starwarswikicompose.domain.model.UnitName.YEARS
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(AndroidJUnit4::class)
class SpecieScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var specieViewModel: SpecieViewModel

    @Test
    fun givenSpecie_whenDisplayed_thenAssertResults(): Unit = runBlocking {
        //given
        val specie = Specie(id = "objectId1", name = "title", language = "language",
                homeworld = UniversalChunk(id = "planetId", name = "planetName"),
                classification = "height", designation = "it was", averageHeight = "height",
                averageLifespan = "lifespan",
                eyeColors = listOf("skins", "skulls"), hairColors = listOf("light", "dark"),
                skinColors = listOf("ground", "stones"),
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "movieId", name = "movieName")))
        )

        specieViewModel = Mockito.mock(SpecieViewModel::class.java)

        val imagesResponse = listOf(ImageSliderModel(1, specie.id, "someUrl"))
        lateinit var imagesFLow: StateFlow<List<ImageSliderModel>>
        val scope = CoroutineScope(coroutineContext)
        flow { emit(imagesResponse) }.stateIn(scope).also { imagesFLow = it }

        specieViewModel.stub {
            on { images } doReturn imagesFLow
        }
        composeTestRule.setContent {
            val navHostController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            SpecieScreenContent(state, scope, snackBarHostState,
                    imagesStateRefresh, Modifier, navHostController, specie, specieViewModel)
        }

        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription("SPECIES")

        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()

        val mainTitle = composeTestRule.onNodeWithText("title")
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val language = composeTestRule.onNodeWithText("Language", useUnmergedTree = true)
        language.assertIsDisplayed()
        language.onSibling().assertTextEquals(specie.language)

        val homeWorld = composeTestRule.onNodeWithText("Home World", useUnmergedTree = true)
        homeWorld.assertIsDisplayed()
        homeWorld.onSibling().assertTextEquals(specie.homeworld.name)

        val classification = composeTestRule.onNodeWithText("Classification", useUnmergedTree = true)
        classification.assertIsDisplayed()
        classification.onSibling().assertTextEquals(specie.classification)

        val designation = composeTestRule.onNodeWithText("Designation", useUnmergedTree = true)
        designation.assertIsDisplayed()
        designation.onSibling().assertTextEquals(specie.designation)

        val averageHeight = composeTestRule.onNodeWithText("Average Height", useUnmergedTree = true)
        averageHeight.assertIsDisplayed()
        averageHeight.onSibling().assertTextEquals("${specie.averageHeight} ${CM.description}")

        val lifespan = composeTestRule.onNodeWithText("Average Lifespan", useUnmergedTree = true)
        lifespan.assertIsDisplayed()
        lifespan.onSibling().assertTextEquals("${specie.averageLifespan} ${YEARS.description}")

        val eyes = composeTestRule.onNodeWithText("Eye Colors", useUnmergedTree = true)
        eyes.assertIsDisplayed()

        val skin = composeTestRule.onNodeWithText("Skin Colors", useUnmergedTree = true)
        skin.assertIsDisplayed()

        val hair = composeTestRule.onNodeWithText("Hair Colors", useUnmergedTree = true)
        hair.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val characterBox = composeTestRule.onNodeWithContentDescription("RelatedBox charName", useUnmergedTree = true)
        characterBox.assertIsDisplayed()
        characterBox.assertIsEnabled()
        characterBox.onSibling().assertTextEquals(specie.characterConnection.objects[0].name)


        val movieBox = composeTestRule.onNodeWithContentDescription("RelatedBox movieName", useUnmergedTree = true)
        movieBox.assertIsDisplayed()
        movieBox.assertIsEnabled()
        movieBox.onSibling().assertTextEquals(specie.movieConnection.objects[0].name)

        val image = composeTestRule.onNodeWithContentDescription("image from flickr",
                substring = true,
                ignoreCase = true,
                useUnmergedTree = true)

        image.assertIsDisplayed()
        image.assertIsEnabled()

        val onClickLeft = composeTestRule.onNodeWithContentDescription("onClickLeft", useUnmergedTree = true)
        onClickLeft.assertIsDisplayed()
        onClickLeft.assertIsEnabled()
        onClickLeft.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickLeft.onParent().assertIsNotFocused()

        val onClickRight = composeTestRule.onNodeWithContentDescription("onCLickRight", useUnmergedTree = true)
        onClickRight.assertIsDisplayed()
        onClickRight.assertIsEnabled()
        onClickRight.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        onClickRight.onParent().assertIsNotFocused()

    }
}