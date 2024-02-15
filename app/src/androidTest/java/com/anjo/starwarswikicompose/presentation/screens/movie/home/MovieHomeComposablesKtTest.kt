package com.anjo.starwarswikicompose.presentation.screens.movie.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.testutils.assertIsVisibleWith
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieHomeComposablesKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenMovies_whenDisplayed_thenAssertOps() {
        //given
        val movies = listOf(UniversalChunk("1", "name1", "desc1"),
                UniversalChunk("2", "name2", "desc2"),
                UniversalChunk("3", "name3", "desc3"))

        val movieState = HomeMovieViewModel.MovieState(movies)

        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeMovieContent(movieState, navController)
        }
        //when and then
        movies.forEach { chunk ->  chunk assertIsVisibleWith composeTestRule }
    }

    @Test
    fun givenEmptyMoviesAndLoading_whenDisplayed_thenAssertOps() {
        //given
        val movieState = HomeMovieViewModel.MovieState(listOf(), true)

        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeMovieContent(movieState, navController)
        }

        //when and then
        composeTestRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.VerticalScrollAxisRange))
                .onChildren().assertCountEquals(3)

    }
}