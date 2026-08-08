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
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.presentation.common.create.models.AddVehicleObject
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.assertChunksField
import com.anjo.starwarswikicompose.testutils.assertListField
import com.anjo.starwarswikicompose.testutils.assertStringField
import com.anjo.starwarswikicompose.testutils.connectionShouldBeSame
import com.anjo.starwarswikicompose.testutils.extractNames
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_IMAGE_OBJECT_TAG
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_VEHICLE_TAG
import com.anjo.starwarswikicompose.utils.TestTags.SAVE_BUTTON_TAG
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
class AddVehicleObjectTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val insertUseCase = mockk<InsertUseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = AddObjectViewModel(useCase, insertUseCase, dispatcher)

    @Test
    fun givenInputs_whenShowAddVehicleObject_thenAssertResults() = runTest(dispatcher) {
        //given
        val slot = slot<VehicleDto>()
        val expected = TestConstants.vehicleDto

        coEvery { useCase.getAllFilmsUseCase() } returns TestConstants.movies
        coEvery { useCase.getAllPeopleUseCase() } returns TestConstants.people
        coEvery { insertUseCase.insertVehicleUseCase(capture(slot)) } returns ""
        coEvery { insertUseCase.insertChunkUseCase(any()) } just awaits

        advanceUntilIdle()

        composeRule.setContent {
            AddVehicleObject(Modifier, viewModel = viewModel, vehicleDto = VehicleDto(isFromLocalStore = true),
                    resetObject = {}) {}
        }

        advanceUntilIdle()

        //when and then
        val root = composeRule.onNodeWithTag(ADD_OBJECT_VEHICLE_TAG, true).onChild()
        root.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))
        root.assertHasNoClickAction()
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollByOffset))

        val name = composeRule.onNodeWithTag("StringFieldVehicle", true)
        assertStringField(name, expected.name)

        val model = composeRule.onNodeWithTag("StringFieldModel", true)
        assertStringField(model, expected.model)

        val vehicleClass = composeRule.onNodeWithTag("StringFieldClass", true)
        assertStringField(vehicleClass, expected.vehicleClass)

        val manufacturers = composeRule.onNodeWithTag("ListFieldManufacturers", true)
        assertListField(manufacturers, expected.manufacturers)

        val cost = composeRule.onNodeWithTag("StringFieldCost", true)
        assertStringField(cost, expected.cost)

        val length = composeRule.onNodeWithTag("StringFieldLength", true)
        assertStringField(length, expected.length)

        val crew = composeRule.onNodeWithTag("StringFieldCrew", true)
        assertStringField(crew, expected.crew)

        val passengers = composeRule.onNodeWithTag("StringFieldPassengers", true)
        assertStringField(passengers, expected.passengers)

        val vMax = composeRule.onNodeWithTag("StringFieldV Max", true)
        assertStringField(vMax, expected.vMax)

        val cargoCapacity = composeRule.onNodeWithTag("StringFieldCargo capacity", true)
        cargoCapacity.performScrollTo()
        assertStringField(cargoCapacity, expected.cargoCapacity)

        val consumables = composeRule.onNodeWithTag("StringFieldConsumables", true)
        consumables.performScrollTo()
        assertStringField(consumables, expected.consumables)

        composeRule.waitForIdle()

        val heroes = composeRule.onNodeWithTag("ChunksField$HEROES_NAME", true)
        heroes.performScrollTo()
        composeRule.waitForIdle()
        assertChunksField(composeRule, heroes, extractNames(expected.characterConnection))

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
        actual.model shouldBe expected.model
        actual.vehicleClass shouldBe expected.vehicleClass
        actual.manufacturers shouldContainAll expected.manufacturers
        actual.cost shouldBe expected.cost
        actual.length shouldBe expected.length
        actual.crew shouldBe expected.crew
        actual.passengers shouldBe expected.passengers
        actual.vMax shouldBe expected.vMax
        actual.cargoCapacity shouldBe expected.cargoCapacity
        actual.consumables shouldBe expected.consumables
        actual.characterConnection connectionShouldBeSame expected.characterConnection
        actual.movieConnection connectionShouldBeSame expected.movieConnection
    }
}