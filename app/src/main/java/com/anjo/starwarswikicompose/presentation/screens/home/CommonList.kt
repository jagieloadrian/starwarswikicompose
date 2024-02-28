package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.presentation.screens.movie.home.Movies
import com.anjo.starwarswikicompose.presentation.screens.person.home.People
import com.anjo.starwarswikicompose.presentation.screens.planet.home.Planets
import com.anjo.starwarswikicompose.presentation.screens.specie.home.Species
import com.anjo.starwarswikicompose.presentation.screens.starship.home.Starships
import com.anjo.starwarswikicompose.presentation.screens.vehicle.home.Vehicles
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonList(
        navController: NavHostController,
        enum: Category,
) {
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            delay(1500)
            isRefreshing = false
        }
    })

    Box(modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)) {
        if (!isRefreshing) {
            ChooseComposableContent(navController, enum, isRefreshing)
        }
        PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun ChooseComposableContent(navController: NavHostController, enum: Category, refresh: Boolean) {
    when (enum) {
        Category.FILMS     -> {
            Movies(navController, refresh = refresh)
        }

        Category.PEOPLE    -> {
            People(navController, refresh = refresh)
        }

        Category.PLANETS   -> {
            Planets(navController, refresh = refresh)
        }

        Category.SPECIES   -> {
            Species(navController, refresh = refresh)
        }

        Category.STARSHIPS -> {
            Starships(navController, refresh = refresh)
        }

        Category.VEHICLES  -> {
            Vehicles(navController, refresh = refresh)
        }
    }
}