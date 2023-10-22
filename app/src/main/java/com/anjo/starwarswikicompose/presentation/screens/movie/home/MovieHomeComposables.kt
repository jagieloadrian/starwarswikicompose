package com.anjo.starwarswikicompose.presentation.screens.movie.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.starwarswikicompose.presentation.common.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.home.CommonButton
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category

@Composable
fun Movies(navController: NavHostController, homeMovieViewModel: HomeMovieViewModel = hiltViewModel(),
           refresh: Boolean) {
    val item by homeMovieViewModel.fetchedFilms.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (item.isLoading) {
            ShimmerEffect()
        } else {
            LazyColumn(contentPadding = PaddingValues(all = SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                items(items = item.movies.orEmpty()) { film ->
                    CommonButton(navController, film, Category.FILMS)
                }
            }
        }
    }
    if (!refresh) {
        homeMovieViewModel.fetchFilms()
    }
}

@Composable
fun FilmColumnText(item: GetAllFilmsQuery.Film, modifier: Modifier) {
    Row(modifier = modifier.fillMaxSize()) {
        Text(text = item.title.toString(),
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName)
        Text(text = item.episodeID.toString(),
                modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName)
    }
}