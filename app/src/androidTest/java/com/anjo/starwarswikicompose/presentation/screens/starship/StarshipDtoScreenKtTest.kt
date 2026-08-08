package com.anjo.starwarswikicompose.presentation.screens.starship

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
import androidx.compose.ui.test.junit4.v2.createComposeRule
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
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.TestConstants.STARSHIP_NAME
import com.anjo.starwarswikicompose.testutils.assertIncludedImage
import com.anjo.starwarswikicompose.testutils.assertRelatedBoxes
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
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
class StarshipDtoScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val imageSliderUseCases = mockk<ImageSliderUseCases>()
    private val removeUseCase = mockk<DeleteUseCases>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val dispatcher = StandardTestDispatcher()
    private val starshipViewModel =
        StarshipViewModel(useCase, imageSliderUseCases, removeUseCase, savedStateHandle, dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenStarshipObject_whenDisplayScreen_thenAssertOps(): Unit = runTest(dispatcher) {
        //given
        val starshipDto = TestConstants.starshipDto.copy("objectId1")
        val imagesResponse = listOf(ImageSliderModel(1, starshipDto.id, "someUrl"))

        coEvery { savedStateHandle.get<String>(DETAILS_STARSHIP_ARGUMENT_KEY) } returns starshipDto.id
        coEvery { useCase.getStarshipUseCase(starshipDto.id) } returns starshipDto
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(starshipDto.id, STARSHIPS) } returns imagesResponse

        starshipViewModel.getStarship()
        advanceUntilIdle()

        val mockNavController = mockk<NavHostController>(relaxed = true)

        composeTestRule.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            StarshipContentScreen(state, this, snackBarHostState,
                    imagesStateRefresh, Modifier, mockNavController, starshipViewModel)
        }

        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription(STARSHIP_NAME)
        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()
        mainImage.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val mainTitle = composeTestRule.onNodeWithText(starshipDto.name)
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val model = composeTestRule.onNodeWithText("Model", useUnmergedTree = true)
        model.assertIsDisplayed()

        val starshipClass = composeTestRule.onNodeWithText("Starship class", useUnmergedTree = true)
        starshipClass.assertIsDisplayed()

        val manufacturers = composeTestRule.onNodeWithText("Manufacturers", useUnmergedTree = true)
        manufacturers.assertIsDisplayed()

        val cost = composeTestRule.onNodeWithText("Cost", useUnmergedTree = true)
        cost.assertIsDisplayed()

        val length = composeTestRule.onNodeWithText("Length", useUnmergedTree = true)
        length.assertIsDisplayed()

        val cargo = composeTestRule.onNodeWithText("Cargo", useUnmergedTree = true)
        cargo.assertIsDisplayed()

        val vMax = composeTestRule.onNodeWithText("V Max", useUnmergedTree = true)
        vMax.assertIsDisplayed()

        val hyperdrive = composeTestRule.onNodeWithText("Hyperdrive Class", useUnmergedTree = true)
        hyperdrive.assertIsDisplayed()

        val megalight = composeTestRule.onNodeWithText("Megalight", useUnmergedTree = true)
        megalight.assertIsDisplayed()

        val crew = composeTestRule.onNodeWithText("Crew", useUnmergedTree = true)
        crew.assertIsDisplayed()

        val passengers = composeTestRule.onNodeWithText("Passengers", useUnmergedTree = true)
        passengers.assertIsDisplayed()

        val consumables = composeTestRule.onNodeWithText("Consumables supply", useUnmergedTree = true)
        consumables.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val characterBoxTitle = composeTestRule.onNodeWithText(HEROES_NAME, useUnmergedTree = true)
        characterBoxTitle.performScrollTo()
        characterBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, starshipDto.characterConnection)

        val moviesBoxTitle = composeTestRule.onNodeWithText(MOVIES_NAME, useUnmergedTree = true)
        moviesBoxTitle.performScrollTo()
        moviesBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, starshipDto.movieConnection)

        assertIncludedImage(composeTestRule)
    }
}