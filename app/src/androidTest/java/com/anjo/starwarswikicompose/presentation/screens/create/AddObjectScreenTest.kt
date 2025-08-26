package com.anjo.starwarswikicompose.presentation.screens.create

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.input.ImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_CATEGORY_DROPDOWN
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_PLANET_TAG
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddObjectScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val insertUseCase = mockk<InsertUseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = AddObjectViewModel(useCase, insertUseCase, dispatcher)

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
    @Test
    fun givenCategoryAndChangeCategory_WhenDisplayed_thenAssertResults() = runTest(dispatcher) {
        //given
        var dismissBool: Boolean? = null
        var dismissCat: Category? = null

        coEvery { useCase.getAllFilmsUseCase() } returns listOf()
        coEvery { useCase.getAllPeopleUseCase() } returns listOf()

        composeRule.setContent {
            AddObjectScreen(modifier = Modifier, category = Category.ALL, viewModel = viewModel) { bool, category ->
                dismissBool = bool
                dismissCat = category
            }
        }

        //when and then
        val image = composeRule.onNodeWithContentDescription("network error icon", true, useUnmergedTree = true)
        image.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
        image.assertIsDisplayed()
        image.assertIsEnabled()

        val categories = composeRule.onNodeWithTag(ADD_OBJECT_CATEGORY_DROPDOWN, useUnmergedTree = true)
                .onChild()
        categories.assertIsDisplayed()
        categories.assertIsEnabled()
        categories.assertTextEquals("ALL")
        categories.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.DropdownList))
        categories.assertHasClickAction()
        categories.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsEditable, false))
        categories.assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Default))
        categories.assertIsNotFocused()

        categories.performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText(Category.PLANETS.categoryName, ignoreCase = true).performClick()
        composeRule.waitForIdle()

        image.assertDoesNotExist()

        val planetBox = composeRule.onNodeWithTag(ADD_OBJECT_PLANET_TAG, true)
                .onChild()
        planetBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))
        planetBox.assertHasNoClickAction()
        planetBox.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
        planetBox.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollByOffset))

        categories.assertTextEquals("PLANETS")

        val dragHandle =
            composeRule.onNodeWithContentDescription("Drag handle", substring = true, useUnmergedTree = true)
        dragHandle.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsContainer, true))

        dragHandle.onParent().performSemanticsAction(SemanticsActions.Dismiss)

        composeRule.waitForIdle()

        dismissBool shouldBe false
        dismissCat shouldBe Category.ALL
    }
}