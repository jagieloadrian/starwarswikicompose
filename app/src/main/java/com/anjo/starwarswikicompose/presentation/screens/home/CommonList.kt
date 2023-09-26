package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
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
            GenerateComposableContent(navController, enum, refreshing)
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun GenerateComposableContent(navController: NavHostController, enum: Category, refreshing: Boolean) {
    val items = collectData(enum)
    when (enum) {
        Category.FILMS     -> Movies(refreshing, navController, items as GetAllFilmsQuery.AllFilms)
        Category.PEOPLES   -> Peoples(refreshing, navController, items as GetAllPeoplesQuery.AllPeople)
        Category.PLANETS   -> Planets(refreshing, navController, items as GetAllPlanetsQuery.AllPlanets)
        Category.SPECIES   -> Species(refreshing, navController, items as GetAllSpeciesQuery.AllSpecies)
        Category.STARSHIPS -> Starships(refreshing, navController, items as GetAllStarshipsQuery.AllStarships)
        Category.VEHICLES  -> Vehicles(refreshing, navController, items as GetAllVehiclesQuery.AllVehicles)
    }
}

@Composable
fun collectData(enum: Category,
                homeViewModel: HomeViewModel = hiltViewModel()): Any {
    when (enum) {
        Category.FILMS     -> {
            homeViewModel.fetchFilms()
            return homeViewModel.fetchedFilms.collectAsState().value
        }

        Category.PEOPLES   -> {
            homeViewModel.fetchPeoples()
            return homeViewModel.fetchedPeoples.collectAsState().value
        }

        Category.PLANETS   -> {
            homeViewModel.fetchPlanets()
            return homeViewModel.fetchedPlanets.collectAsState().value
        }

        Category.SPECIES   -> {
            homeViewModel.fetchSpecies()
            return homeViewModel.fetchedSpecies.collectAsState().value
        }

        Category.STARSHIPS -> {
            homeViewModel.fetchStarships()
            return homeViewModel.fetchedStarships.collectAsState().value
        }

        Category.VEHICLES  -> {
            homeViewModel.fetchVehicles()
            return homeViewModel.fetchedVehicles.collectAsState().value
        }
    }
}
