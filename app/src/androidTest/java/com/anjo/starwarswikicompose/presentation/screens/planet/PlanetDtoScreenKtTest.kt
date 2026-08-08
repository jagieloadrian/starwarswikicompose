package com.anjo.starwarswikicompose.presentation.screens.planet

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
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.TestConstants.PLANET_NAME
import com.anjo.starwarswikicompose.testutils.assertIncludedImage
import com.anjo.starwarswikicompose.testutils.assertRelatedBoxes
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
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
class PlanetDtoScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val imageSliderUseCases = mockk<ImageSliderUseCases>()
    private val removeUseCase = mockk<DeleteUseCases>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = PlanetViewModel(useCase, imageSliderUseCases, removeUseCase, savedStateHandle, dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenPlanet_whenDisplayed_thenAsserResults(): Unit = runTest(dispatcher) {
        //given
        val planetDto = TestConstants.planetDto.copy("objectId1")
        val imagesResponse = listOf(ImageSliderModel(1, planetDto.id, "someUrl"))

        coEvery { savedStateHandle.get<String>(DETAILS_PLANET_ARGUMENT_KEY) } returns planetDto.id
        coEvery { useCase.getPlanetUseCase(planetDto.id) } returns planetDto
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(planetDto.id, PLANETS) } returns imagesResponse

        viewModel.getPlanet()
        advanceUntilIdle()

        val mockNavController = mockk<NavHostController>(relaxed = true)

        composeTestRule.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            PlanetScreenContent(state, this, snackBarHostState,
                    imagesStateRefresh, Modifier, mockNavController, viewModel)
        }
        //when and then
        val mainImage = composeTestRule.onNodeWithContentDescription(PLANET_NAME)
        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()
        mainImage.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val mainTitle = composeTestRule.onNodeWithText(planetDto.name)
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val diameter = composeTestRule.onNodeWithText("Diameter", useUnmergedTree = true)
        diameter.assertIsDisplayed()

        val gravity = composeTestRule.onNodeWithText("Gravity", useUnmergedTree = true)
        gravity.assertIsDisplayed()

        val population = composeTestRule.onNodeWithText("Population", useUnmergedTree = true)
        population.assertIsDisplayed()

        val rotationPeriod = composeTestRule.onNodeWithText("Rotation Period", useUnmergedTree = true)
        rotationPeriod.assertIsDisplayed()

        val orbitalPeriod = composeTestRule.onNodeWithText("Orbital Period", useUnmergedTree = true)
        orbitalPeriod.assertIsDisplayed()

        val climates = composeTestRule.onNodeWithText("Climates", useUnmergedTree = true)
        climates.assertIsDisplayed()

        val surfaceWater = composeTestRule.onNodeWithText("Surface Water", useUnmergedTree = true)
        surfaceWater.assertIsDisplayed()

        val terrains = composeTestRule.onNodeWithText("Terrains", useUnmergedTree = true)
        terrains.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        val characterBoxTitle = composeTestRule.onNodeWithText(HEROES_NAME, useUnmergedTree = true)
        characterBoxTitle.performScrollTo()
        characterBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, planetDto.characterConnection)

        val moviesBoxTitle = composeTestRule.onNodeWithText(MOVIES_NAME, useUnmergedTree = true)
        moviesBoxTitle.performScrollTo()
        moviesBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeTestRule, planetDto.movieConnection)

        assertIncludedImage(composeTestRule)
    }
}