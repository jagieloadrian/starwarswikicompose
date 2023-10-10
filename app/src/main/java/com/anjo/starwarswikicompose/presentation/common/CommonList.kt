package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.*
import com.anjo.starwarswikicompose.presentation.screens.home.*
import com.anjo.starwarswikicompose.utils.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonList(
        navController: NavHostController,
        enum: Category
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() {
        refreshScope.launch {
            refreshing = true
            delay(1500)
            refreshing = false
        }
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(modifier = Modifier.pullRefresh(state).fillMaxSize()) {
        if (!refreshing) {
            generateComposableContent(navController, enum, refreshing)
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun generateComposableContent(navController: NavHostController, enum: Category, refreshing: Boolean) {
    val items = collectData(enum)
    when (enum) {
        Category.FILMS     -> movies(refreshing, navController, items as GetAllFilmsQuery.AllFilms)
        Category.PEOPLES   -> peoples(refreshing, navController, items as GetAllPeoplesQuery.AllPeople)
        Category.PLANETS   -> planets(refreshing, navController, items as GetAllPlanetsQuery.AllPlanets)
        Category.SPECIES   -> species(refreshing, navController, items as GetAllSpeciesQuery.AllSpecies)
        Category.STARSHIPS -> starships(refreshing, navController, items as GetAllStarshipsQuery.AllStarships)
        Category.VEHICLES  -> vehicles(refreshing, navController, items as GetAllVehiclesQuery.AllVehicles)
    }
}

@Composable
fun collectData(enum: Category,
                homeViewModel: HomeViewModel = hiltViewModel()): Any {
    return when (enum) {
        Category.FILMS     -> {
            homeViewModel.fetchFilms()
            homeViewModel.fetchedFilms
        }

        Category.PEOPLES   -> {
            homeViewModel.fetchPeoples()
            homeViewModel.fetchedPeoples
        }

        Category.PLANETS   -> {
            homeViewModel.fetchPlanets()
            homeViewModel.fetchedPlanets
        }

        Category.SPECIES   -> {
            homeViewModel.fetchSpecies()
            homeViewModel.fetchedSpecies
        }

        Category.STARSHIPS -> {
            homeViewModel.fetchStarships()
            homeViewModel.fetchedStarships
        }

        Category.VEHICLES  -> {
            homeViewModel.fetchVehicles()
            homeViewModel.fetchedVehicles
        }
    }
}
