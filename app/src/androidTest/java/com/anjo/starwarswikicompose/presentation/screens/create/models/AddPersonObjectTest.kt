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
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.presentation.common.create.models.AddPersonObject
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.assertChunkField
import com.anjo.starwarswikicompose.testutils.assertChunksField
import com.anjo.starwarswikicompose.testutils.assertStringField
import com.anjo.starwarswikicompose.testutils.connectionShouldBeSame
import com.anjo.starwarswikicompose.testutils.extractNames
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_IMAGE_OBJECT_TAG
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_PERSON_TAG
import com.anjo.starwarswikicompose.utils.TestTags.SAVE_BUTTON_TAG
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
class AddPersonObjectTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val insertUseCase = mockk<InsertUseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = AddObjectViewModel(useCase, insertUseCase, dispatcher)

    @Test
    fun givenInputs_whenShowAddPersonObject_thenAssertResults() = runTest(dispatcher) {
        //given
        val slot = slot<PersonDto>()
        val expected = TestConstants.personDto

        coEvery { useCase.getAllFilmsUseCase() } returns TestConstants.movies
        coEvery { useCase.getAllPlanetsUseCase() } returns TestConstants.planet
        coEvery { useCase.getAllStarshipsUseCase() } returns TestConstants.starship
        coEvery { useCase.getAllVehicleUseCase() } returns TestConstants.vehicle
        coEvery { useCase.getAllSpeciesUseCase() } returns TestConstants.specie
        coEvery { insertUseCase.insertPersonUseCase(capture(slot)) } returns ""
        coEvery { insertUseCase.insertChunkUseCase(any()) } just awaits

        advanceUntilIdle()

        composeRule.setContent {
            AddPersonObject(Modifier, viewModel = viewModel, personDto = PersonDto(isFromLocalStore = true),
                    resetObject = {}) {}
        }

        advanceUntilIdle()

        //when and then
        val root = composeRule.onNodeWithTag(ADD_OBJECT_PERSON_TAG, true).onChild()
        root.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))
        root.assertHasNoClickAction()
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollByOffset))

        val hero = composeRule.onNodeWithTag("StringFieldHero", true)
        assertStringField(hero, expected.name)

        val homeworld = composeRule.onNodeWithTag("ChunkFieldHomeworld", true)
        assertChunkField(composeRule, homeworld, expected.homeworld)

        val specie = composeRule.onNodeWithTag("ChunkFieldSpecie", true)
        assertChunkField(composeRule, specie, expected.specie)

        val birthYear = composeRule.onNodeWithTag("StringFieldBirth Year", true)
        assertStringField(birthYear, expected.birthYear)

        val height = composeRule.onNodeWithTag("StringFieldHeight", true)
        assertStringField(height, expected.height)

        val mass = composeRule.onNodeWithTag("StringFieldMass", true)
        assertStringField(mass, expected.mass)

        val gender = composeRule.onNodeWithTag("StringFieldGender", true)
        assertStringField(gender, expected.gender)

        val hair = composeRule.onNodeWithTag("StringFieldHair", true)
        assertStringField(hair, expected.hair)

        val skin = composeRule.onNodeWithTag("StringFieldSkin", true)
        assertStringField(skin, expected.skin)

        val starships = composeRule.onNodeWithTag("ChunksField$STARSHIPS_NAME", true)
        starships.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, starships, extractNames(expected.starshipConnection))

        val vehicles = composeRule.onNodeWithTag("ChunksField$VEHICLES_NAME", true)
        vehicles.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, vehicles, extractNames(expected.vehicleConnection))

        val movies = composeRule.onNodeWithTag("ChunksField$MOVIES_NAME", true)
        movies.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, movies, extractNames(expected.movieConnection))

        val addImage = composeRule.onNodeWithTag(ADD_IMAGE_OBJECT_TAG, true)
        addImage.assertIsNotFocused()
        addImage.assertHasClickAction()
        addImage.onChild().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))

        val saveButton = composeRule.onNodeWithTag(SAVE_BUTTON_TAG, useUnmergedTree = true)
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
        actual.name shouldBe expected.name
        actual.homeworld shouldBe expected.homeworld
        actual.specie shouldBe expected.specie
        actual.birthYear shouldBe expected.birthYear
        actual.height shouldBe expected.height
        actual.mass shouldBe expected.mass
        actual.gender shouldBe expected.gender
        actual.hair shouldBe expected.hair
        actual.skin shouldBe expected.skin
        actual.starshipConnection connectionShouldBeSame expected.starshipConnection
        actual.vehicleConnection connectionShouldBeSame expected.vehicleConnection
        actual.movieConnection connectionShouldBeSame expected.movieConnection
    }
}