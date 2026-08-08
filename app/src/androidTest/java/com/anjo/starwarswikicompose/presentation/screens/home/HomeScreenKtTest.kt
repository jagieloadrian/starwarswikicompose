package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.semantics.Role.Companion.DropdownList
import androidx.compose.ui.semantics.SemanticsProperties.IsTraversalGroup
import androidx.compose.ui.semantics.SemanticsProperties.Role
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.utils.Constants.BANNER_NOT_FOUND_TEXT
import com.anjo.starwarswikicompose.utils.TestTags.BANNER_BOX_TAG
import com.anjo.starwarswikicompose.utils.TestTags.CHUNK_LIST
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val useCase = mockk<UseCases>()
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = HomeScreenViewModel(useCase, dispatcher)

    @Test
    fun `when run home screen then display 3 of movies`() = runTest(dispatcher) {
        //given
        val films = listOf(
                UniversalChunkDto(id = "objectId1", name = "name1", desc = "1", FILMS),
                UniversalChunkDto(id = "objectId2", name = "name2", desc = "2", FILMS),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "3", FILMS))
        coEvery { useCase.getAllFilmsUseCase() } returns films

        val mockNavController = mockk<NavHostController>(relaxed = true)

        composeTestRule.setContent {
            HomeContentScreen(PaddingValues(), mockNavController, viewModel)
        }

        val catDropDown = composeTestRule.onNodeWithText("FILMS")
        catDropDown.assertExists()
        catDropDown.assertHasClickAction()
        catDropDown.assert(SemanticsMatcher.expectValue(Role, DropdownList))

        val chunkList = composeTestRule.onNodeWithTag(CHUNK_LIST)
        chunkList.assert(SemanticsMatcher.expectValue(IsTraversalGroup, true))

        chunkList.fetchSemanticsNode().children.isEmpty() shouldBe true

        advanceUntilIdle()

        composeTestRule.waitUntil(5000) {
            composeTestRule
                    .onNodeWithText("name1")
                    .isDisplayed()
        }

        films.forEach {
            val component = composeTestRule.onNodeWithText(it.name)
            component.assertExists()
            component.assertHasClickAction()
        }
        val banner = composeTestRule.onNodeWithTag(BANNER_BOX_TAG, true)

        banner.assertExists()
        banner.assertIsNotFocused()
        banner.assertHasClickAction()

        banner.onChild().assertTextContains(BANNER_NOT_FOUND_TEXT)
        banner.performClick()

        banner.assertDoesNotExist()

        val firstComponent = composeTestRule.onNodeWithText(films.first().name)
        firstComponent.performClick()

        verify { mockNavController.navigate("details_movie/objectId1") }
    }
}