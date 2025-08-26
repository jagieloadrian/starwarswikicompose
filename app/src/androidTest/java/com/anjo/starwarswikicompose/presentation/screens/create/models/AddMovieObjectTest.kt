@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.create.models

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.presentation.common.create.models.AddMovieObject
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.assertChunksField
import com.anjo.starwarswikicompose.testutils.assertListField
import com.anjo.starwarswikicompose.testutils.assertStringField
import com.anjo.starwarswikicompose.testutils.connectionShouldBeSame
import com.anjo.starwarswikicompose.testutils.extractNames
import com.anjo.starwarswikicompose.utils.Constants.ADD_NEW_MOVIE
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.PLANETS_NAME
import com.anjo.starwarswikicompose.utils.Constants.SPECIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_IMAGE_OBJECT_TAG
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_MOVIE_TAG
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddMovieObjectTest {
    @get:Rule
    val composeRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val insertUseCase = mockk<InsertUseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = AddObjectViewModel(useCase, insertUseCase, dispatcher)

    @Test
    fun givenInputs_whenShowAddMovieObject_thenAssertResults() = runTest(dispatcher) {
        //given
        val slot = slot<MovieDto>()
        val expected = TestConstants.movieDto

        coEvery { useCase.getAllPeopleUseCase() } returns TestConstants.people
        coEvery { useCase.getAllPlanetsUseCase() } returns TestConstants.planet
        coEvery { useCase.getAllStarshipsUseCase() } returns TestConstants.starship
        coEvery { useCase.getAllVehicleUseCase() } returns TestConstants.vehicle
        coEvery { useCase.getAllSpeciesUseCase() } returns TestConstants.specie
        coEvery { insertUseCase.insertMovieUseCase(capture(slot)) } returns ""
        coEvery { insertUseCase.insertChunkUseCase(any()) } just awaits

        advanceUntilIdle()

        composeRule.setContent {
            AddMovieObject(Modifier, viewModel = viewModel, movieDto = MovieDto(isFromLocalStore = true),
                    resetObject = {}) {

            }
        }

        advanceUntilIdle()

        //when and then
        val root = composeRule.onNodeWithTag(ADD_OBJECT_MOVIE_TAG, true).onChild()
        root.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))
        root.assertHasNoClickAction()
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollByOffset))

        val title = composeRule.onNodeWithTag("StringFieldTitle", true)
        assertStringField(title, expected.title)

        val episodeNo = composeRule.onNodeWithTag("StringFieldEpisode no", true)
        assertStringField(episodeNo, expected.episodeId)

        val openingCrawl = composeRule.onNodeWithTag("StringFieldOpening crawl", true)
        assertStringField(openingCrawl, expected.openingCrawl)

        val producers = composeRule.onNodeWithTag("ListFieldProducers", true)
        assertListField(producers, expected.producers)

        val director = composeRule.onNodeWithTag("StringFieldDirector", true)
        assertStringField(director, expected.director)

        val releaseDate = composeRule.onNodeWithTag("StringFieldRelease Date", true)
        assertStringField(releaseDate, expected.releaseDate)

        composeRule.waitForIdle()

        val heroes = composeRule.onNodeWithTag("ChunksField$HEROES_NAME", true)
        heroes.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, heroes, extractNames(expected.characterConnection))

        val planets = composeRule.onNodeWithTag("ChunksField$PLANETS_NAME", true)
        planets.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, planets, extractNames(expected.planetConnection))

        val starships = composeRule.onNodeWithTag("ChunksField$STARSHIPS_NAME", true)
        starships.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, starships, extractNames(expected.starshipConnection))

        val vehicle = composeRule.onNodeWithTag("ChunksField$VEHICLES_NAME", true)
        vehicle.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, vehicle, extractNames(expected.vehicleConnection))

        val species = composeRule.onNodeWithTag("ChunksField$SPECIES_NAME", true)
        species.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, species, extractNames(expected.specieConnection))

        val addImage = composeRule.onNodeWithTag(ADD_IMAGE_OBJECT_TAG, true)
        addImage.assertIsNotFocused()
        addImage.assertHasClickAction()
        addImage.onChild().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val saveButton = composeRule.onNodeWithText(ADD_NEW_MOVIE, useUnmergedTree = true).onParent()
        saveButton.performScrollTo()
        composeRule.waitForIdle()

        saveButton.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
        saveButton.assertIsNotFocused()
        saveButton.assertHasClickAction()

        saveButton.performClick()
        composeRule.waitForIdle()

        advanceUntilIdle()
        val actual = slot.captured

        actual.shouldNotBeNull()
        actual.title shouldBe expected.title
        actual.episodeId shouldBe expected.episodeId
        actual.openingCrawl shouldBe expected.openingCrawl
        actual.producers shouldContainAll expected.producers
        actual.director shouldBe expected.director
        actual.releaseDate shouldBe expected.releaseDate
        actual.characterConnection connectionShouldBeSame expected.characterConnection
        actual.planetConnection connectionShouldBeSame expected.planetConnection
        actual.starshipConnection connectionShouldBeSame expected.starshipConnection
        actual.vehicleConnection connectionShouldBeSame expected.vehicleConnection
        actual.specieConnection connectionShouldBeSame expected.specieConnection
    }
}