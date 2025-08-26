@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.movie

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
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
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.domain.model.sw.SourceType.APOLLO
import com.anjo.starwarswikicompose.services.usecases.deleteusecase.DeleteUseCases
import com.anjo.starwarswikicompose.services.usecases.imagesliderusecase.ImageSliderUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.TestConstants.MOVIE_NAME
import com.anjo.starwarswikicompose.testutils.assertIncludedImage
import com.anjo.starwarswikicompose.testutils.assertRelatedBoxes
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.PLANETS_NAME
import com.anjo.starwarswikicompose.utils.Constants.SOURCE_TYPE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.SPECIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
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
class MovieDtoScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val imageSliderUseCases = mockk<ImageSliderUseCases>()
    private val removeUseCase = mockk<DeleteUseCases>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = MovieViewModel(useCase, imageSliderUseCases, removeUseCase, savedStateHandle, dispatcher)

    @Test
    fun givenMovie_whenDisplayed_thenAssertResults(): Unit = runTest(dispatcher) {
        //given
        val movieDto = TestConstants.movieDto.copy(id = "objectId1")
        val imagesResponse = listOf(ImageSliderModel(1, movieDto.id, "someUrl"))

        coEvery { savedStateHandle.get<String>(DETAILS_MOVIE_ARGUMENT_KEY) } returns movieDto.id
        coEvery { savedStateHandle.get<SourceType>(SOURCE_TYPE_ARGUMENT_KEY) } returns APOLLO
        coEvery { useCase.getMovieUseCase(movieDto.id) } returns movieDto
        coEvery { imageSliderUseCases.getImagesForObjectUseCase(movieDto.id, FILMS) } returns imagesResponse

        viewModel.getMovie()
        advanceUntilIdle()

        val mockNavController = mockk<NavHostController>(relaxed = true)

        composeRule.setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val imagesStateRefresh = remember { mutableStateOf(true) }
            val state = rememberScrollState()
            MovieScreen(state, this, snackBarHostState,
                    imagesStateRefresh, Modifier, mockNavController, viewModel)
        }
        //when and then
        val mainImage = composeRule.onNodeWithContentDescription(MOVIE_NAME, useUnmergedTree = true)
        mainImage.assertIsDisplayed()
        mainImage.assertIsEnabled()
        mainImage.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val mainTitle = composeRule.onNodeWithText(movieDto.title, useUnmergedTree = true)
        mainTitle.assertIsDisplayed()
        mainTitle.assertIsEnabled()

        val episodeBox = composeRule.onNodeWithText("Episode No", useUnmergedTree = true)
        episodeBox.assertIsDisplayed()

        val episodeBoxContent = composeRule.onNodeWithText(movieDto.episodeId, useUnmergedTree = true)
        episodeBoxContent.assertIsDisplayed()

        val openingCrawl = composeRule.onNodeWithText("Opening Crawl", useUnmergedTree = true)
        openingCrawl.assertIsDisplayed()
        openingCrawl.onSibling().assertTextEquals(movieDto.openingCrawl)
        openingCrawl.onParent().assertHasClickAction()
        openingCrawl.onParent().assertIsNotFocused()

        val producersName = composeRule.onNodeWithText("Producers")
        producersName.assertIsDisplayed()

        val producers = composeRule.onNodeWithText("producer1", substring = true, ignoreCase = true)
        producers.assertIsDisplayed()
        producers.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))

        val director = composeRule.onNodeWithText("Director")
        director.assertIsDisplayed()

        val directorName = composeRule.onNodeWithText(movieDto.director, useUnmergedTree = true)
        directorName.assertIsDisplayed()

        val releaseDate = composeRule.onNodeWithText("Release Date", useUnmergedTree = true)
        releaseDate.assertIsDisplayed()
        releaseDate.assertIsEnabled()

        val releaseDateValue = composeRule.onNodeWithText(movieDto.releaseDate, useUnmergedTree = true)
        releaseDateValue.assertIsDisplayed()

        val characterBoxTitle = composeRule.onNodeWithText(HEROES_NAME, useUnmergedTree = true)
        characterBoxTitle.performScrollTo()
        characterBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeRule, movieDto.characterConnection)

        composeRule.onRoot().performTouchInput { swipeUp() }

        val planetsBoxTitle = composeRule.onNodeWithText(PLANETS_NAME, useUnmergedTree = true)
        planetsBoxTitle.performScrollTo()
        planetsBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeRule, movieDto.planetConnection)

        val starshipsBoxTitle = composeRule.onNodeWithText(STARSHIPS_NAME, useUnmergedTree = true)
        starshipsBoxTitle.performScrollTo()
        starshipsBoxTitle.assertIsDisplayed()
        assertRelatedBoxes(composeRule, movieDto.starshipConnection)

        val vehiclesBoxTitle = composeRule.onNodeWithText(VEHICLES_NAME, useUnmergedTree = true)
        vehiclesBoxTitle.assertIsDisplayed()
        vehiclesBoxTitle.performScrollTo()
        assertRelatedBoxes(composeRule, movieDto.vehicleConnection)

        val speciesBoxTitle = composeRule.onNodeWithText(SPECIES_NAME, useUnmergedTree = true)
        speciesBoxTitle.assertIsDisplayed()
        speciesBoxTitle.performScrollTo()
        assertRelatedBoxes(composeRule, movieDto.specieConnection)

        advanceUntilIdle()

        assertIncludedImage(composeRule)
    }
}