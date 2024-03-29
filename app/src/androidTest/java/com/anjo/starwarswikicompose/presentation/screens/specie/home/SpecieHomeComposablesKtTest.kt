package com.anjo.starwarswikicompose.presentation.screens.specie.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.testutils.assertIsVisibleWith
import com.anjo.starwarswikicompose.utils.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpecieHomeComposablesKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenSpecies_whenDisplayed_thenAssertOps() {
        //given
        val species = listOf(UniversalChunk("1", "name1", "desc1"),
                UniversalChunk("2", "name2", "desc2"),
                UniversalChunk("3", "name3", "desc3"))
        val specieState = HomeSpecieViewModel.SpecieState(species)
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeSpecieContent(specieState, navController)
        }

        //when and then
        species.forEach { chunk -> chunk assertIsVisibleWith composeTestRule }
        val searchbar = composeTestRule.onNodeWithTag(Constants.SEARCH_BAR_LABEL, true)
        searchbar.assertIsNotFocused()
        searchbar.onChild().performTextReplacement("2")
        searchbar.onChild().assertTextEquals("2")
    }

    @Test
    fun givenEmptySpeciesAndLoading_whenDisplayed_thenAssertOps() {
        //given
        val specieState = HomeSpecieViewModel.SpecieState(listOf(), true)
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeSpecieContent(specieState, navController)
        }

        //when and then
        composeTestRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.VerticalScrollAxisRange))
                .onChildren().assertCountEquals(3)
    }
}