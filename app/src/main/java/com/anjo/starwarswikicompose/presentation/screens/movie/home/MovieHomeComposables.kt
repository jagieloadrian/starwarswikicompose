package com.anjo.starwarswikicompose.presentation.screens.movie.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

@Composable
fun Movies(
        navController: NavHostController, homeMovieViewModel: HomeMovieViewModel = hiltViewModel(),
        refresh: Boolean,
) {
    val item by homeMovieViewModel.fetchedFilms.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        homeMovieViewModel.getMovies()
        init.value = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        } else {
            LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                items(items = item.movies) { item ->
                    CommonButton(navController, item, Category.FILMS)
                }
            }
        }
    }
    if (!refresh) {
        homeMovieViewModel.fetchFilms()
    }
}