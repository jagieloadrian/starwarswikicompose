package com.anjo.starwarswikicompose.presentation.screens.planet.home

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
class PlanetHomeComposablesKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPlanets_whenDisplayed_thenAssertOps() {
        //given
        val planets = listOf(UniversalChunk("1", "name1", "desc1"),
                UniversalChunk("2", "name2", "desc2"),
                UniversalChunk("3", "name3", "desc3"))
        val planetState = HomePlanetViewModel.PlanetState(planets)

        composeTestRule.setContent {
            val navController = rememberNavController()
            HomePlanetContent(planetState, navController)
        }

        //when and then
        planets.forEach { chunk -> chunk assertIsVisibleWith composeTestRule }
        val searchbar = composeTestRule.onNodeWithTag(Constants.SEARCH_BAR_LABEL, true)
        searchbar.assertIsNotFocused()
        searchbar.onChild().performTextReplacement("2")
        searchbar.onChild().assertTextEquals("2")
    }

    @Test
    fun givenEmptyPlanetsAndLoading_whenDisplayed_thenAssertOps() {
        //given
        val planetState = HomePlanetViewModel.PlanetState(listOf(), true)
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomePlanetContent(planetState, navController)
        }

        //when and then
        composeTestRule.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.VerticalScrollAxisRange))[0]
                .onChildren().assertCountEquals(3)

    }
}