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
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.presentation.common.create.models.AddSpecieObject
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.testutils.TestConstants
import com.anjo.starwarswikicompose.testutils.assertChunkField
import com.anjo.starwarswikicompose.testutils.assertChunksField
import com.anjo.starwarswikicompose.testutils.assertListField
import com.anjo.starwarswikicompose.testutils.assertStringField
import com.anjo.starwarswikicompose.testutils.connectionShouldBeSame
import com.anjo.starwarswikicompose.testutils.extractNames
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.ADD_IMAGE_OBJECT_TAG
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_SPECIE_TAG
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
class AddSpecieObjectTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val insertUseCase = mockk<InsertUseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = AddObjectViewModel(useCase, insertUseCase, dispatcher)

    @Test
    fun givenInputs_whenShowAddSpecieObject_thenAssertResults() = runTest(dispatcher) {
        //given
        val slot = slot<SpecieDto>()
        val expected = TestConstants.specieDto

        coEvery { useCase.getAllFilmsUseCase() } returns TestConstants.movies
        coEvery { useCase.getAllPlanetsUseCase() } returns TestConstants.planet
        coEvery { useCase.getAllPeopleUseCase() } returns TestConstants.people
        coEvery { insertUseCase.insertSpecieUseCase(capture(slot)) } returns ""
        coEvery { insertUseCase.insertChunkUseCase(any()) } just awaits

        advanceUntilIdle()

        composeRule.setContent {
            AddSpecieObject(Modifier, viewModel = viewModel, specieDto = SpecieDto(isFromLocalStore = true),
                    resetObject = {}) {}
        }

        advanceUntilIdle()

        //when and then
        val root = composeRule.onNodeWithTag(ADD_OBJECT_SPECIE_TAG, true).onChild()
        root.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))
        root.assertHasNoClickAction()
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
        root.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollByOffset))

        val specie = composeRule.onNodeWithTag("StringFieldSpecie", true)
        assertStringField(specie, expected.name)

        val language = composeRule.onNodeWithTag("StringFieldLanguage", true)
        assertStringField(language, expected.language)

        val homeworld = composeRule.onNodeWithTag("ChunkFieldHomeworld", true)
        assertChunkField(composeRule, homeworld, expected.homeworld)

        val classification = composeRule.onNodeWithTag("StringFieldClassification", true)
        assertStringField(classification, expected.classification)

        val designation = composeRule.onNodeWithTag("StringFieldDesignation", true)
        assertStringField(designation, expected.designation)

        val averageHeight = composeRule.onNodeWithTag("StringFieldAvg height", true)
        assertStringField(averageHeight, expected.averageHeight)

        val averageLifespan = composeRule.onNodeWithTag("StringFieldAvg lifespan", true)
        assertStringField(averageLifespan, expected.averageLifespan)

        val eyeColors = composeRule.onNodeWithTag("ListFieldEye colors", true)
        assertListField(eyeColors, expected.eyeColors)

        val hairColors = composeRule.onNodeWithTag("ListFieldHair colors", true)
        assertListField(hairColors, expected.hairColors)

        val skinColors = composeRule.onNodeWithTag("ListFieldSkin colors", true)
        skinColors.performScrollTo()
        assertListField(skinColors, expected.skinColors)

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
        actual.language shouldBe expected.language
        actual.homeworld shouldBe expected.homeworld
        actual.classification shouldBe expected.classification
        actual.designation shouldBe expected.designation
        actual.averageHeight shouldBe expected.averageHeight
        actual.averageLifespan shouldBe expected.averageLifespan
        actual.eyeColors shouldContainAll expected.eyeColors
        actual.hairColors shouldContainAll expected.hairColors
        actual.skinColors shouldContainAll expected.skinColors
        actual.characterConnection connectionShouldBeSame expected.characterConnection
        actual.movieConnection connectionShouldBeSame expected.movieConnection
    }
}