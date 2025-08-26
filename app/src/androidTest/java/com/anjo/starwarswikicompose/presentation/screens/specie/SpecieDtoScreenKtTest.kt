package com.anjo.starwarswikicompose.presentation.screens.specie

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.TestConstants.SPECIE_NAME
import com.anjo.starwarswikicompose.testutils.assertIncludedImage
import com.anjo.starwarswikicompose.testutils.assertRelatedBoxes
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_SPECIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
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
class SpecieDtoScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val imageSliderUseCases = mockk<ImageSliderUseCases>()
    private val removeUseCase = mockk<DeleteUseCases>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = SpecieViewModel(useCase, imageSliderUseCases, removeUseCase, savedStateHandle, dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenSpecie_whenDisplayed_thenAssertResults(): Unit = runTest(dispatcher) {
        //given
        val specieDto = TestConstants.specieDto.copy("objectId1")
        val imagesResponse = listOf(ImageSliderModel(1, specieDto.id, "someUrl"))

        coEvery { savedStateHandle.get<String>(DETAILS_SPECIE_ARGUMENT_KEY) } returns specieDto.id
        coEvery { useCase.getSpecieUseCase(specieDto.id) } returns specieDto
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(specieDto.id, SPECIES) } returns imagesResponse

        viewModel.getSpecie()
        advanceUntilIdle()

        val mockNavController = mockk<NavHostController>(relaxed = true)

        composeTestRule.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            SpecieScreenContent(state, this, snackBarHostState,
                    imagesStateRefresh, Modifier, mockNavController, viewModel)
        }

        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription(SPECIE_NAME)
        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()
        mainImage.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val mainTitle = composeTestRule.onNodeWithText(specieDto.name)
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val language = composeTestRule.onNodeWithText("Language", useUnmergedTree = true)
        language.assertIsDisplayed()

        val homeWorld = composeTestRule.onNodeWithText("Home World", useUnmergedTree = true)
        homeWorld.assertIsDisplayed()

        val classification = composeTestRule.onNodeWithText("Classification", useUnmergedTree = true)
        classification.assertIsDisplayed()

        val designation = composeTestRule.onNodeWithText("Designation", useUnmergedTree = true)
        designation.assertIsDisplayed()

        val averageHeight = composeTestRule.onNodeWithText("Average Height", useUnmergedTree = true)
        averageHeight.assertIsDisplayed()

        val lifespan = composeTestRule.onNodeWithText("Average Lifespan", useUnmergedTree = true)
        lifespan.assertIsDisplayed()

        val eyes = composeTestRule.onNodeWithText("Eye Colors", useUnmergedTree = true)
        eyes.assertIsDisplayed()

        val skin = composeTestRule.onNodeWithText("Skin Colors", useUnmergedTree = true)
        skin.assertIsDisplayed()

        val hair = composeTestRule.onNodeWithText("Hair Colors", useUnmergedTree = true)
        hair.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val characterBoxTitle = composeTestRule.onNodeWithText(HEROES_NAME, useUnmergedTree = true)
        characterBoxTitle.performScrollTo()
        characterBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, specieDto.characterConnection)

        val moviesBoxTitle = composeTestRule.onNodeWithText(MOVIES_NAME, useUnmergedTree = true)
        moviesBoxTitle.performScrollTo()
        moviesBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, specieDto.movieConnection)

        assertIncludedImage(composeTestRule)
    }
}