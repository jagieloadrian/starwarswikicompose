package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.presentation.common.EmptyScreen
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
            delay(2500)
            refreshing = false
        }
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
            targetValue = if (startAnimation) ContentAlpha.high else 0f,
            animationSpec = tween(
                    durationMillis = 2000
            )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }


    Box(modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize()
            .alpha(alphaAnim)) {
        if (!refreshing) {
            GenerateComposableContent(navController, enum)
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun GenerateComposableContent(navController: NavHostController, enum: Category) {
    val items: Any = collectData(enum)
    if (checkIfBodyIsEmpty(items, enum)) {
        EmptyScreen(enum)
        return
    }
    when (enum) {
        Category.FILMS     -> {
            Movies(navController, items as GetAllFilmsQuery.AllFilms)
            return
        }
        Category.PEOPLE    -> {
            People(navController, items as GetAllPeoplesQuery.AllPeople)
            return
        }
        Category.PLANETS   -> {
            Planets(navController, items as GetAllPlanetsQuery.AllPlanets)
            return
        }
        Category.SPECIES   -> {
            Species(navController, items as GetAllSpeciesQuery.AllSpecies)
            return
        }
        Category.STARSHIPS -> {
            Starships(navController, items as GetAllStarshipsQuery.AllStarships)
            return
        }
        Category.VEHICLES  -> {
            Vehicles(navController, items as GetAllVehiclesQuery.AllVehicles)
            return
        }
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

        Category.PEOPLE    -> {
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

fun checkIfBodyIsEmpty(items: Any, enum: Category): Boolean {
    return when (enum) {
        Category.FILMS     -> {
            val currentItems = items as GetAllFilmsQuery.AllFilms
            currentItems.totalCount == 0
        }

        Category.PEOPLE    -> {
            val currentItems = items as GetAllPeoplesQuery.AllPeople
            currentItems.totalCount == 0
        }

        Category.PLANETS   -> {
            val currentItems = items as GetAllPlanetsQuery.AllPlanets
            currentItems.totalCount == 0
        }

        Category.SPECIES   -> {
            val currentItems = items as GetAllSpeciesQuery.AllSpecies
            currentItems.totalCount == 0
        }

        Category.STARSHIPS -> {
            val currentItems = items as GetAllStarshipsQuery.AllStarships
            currentItems.totalCount == 0
        }

        Category.VEHICLES  -> {
            val currentItems = items as GetAllVehiclesQuery.AllVehicles
            currentItems.totalCount == 0
        }
    }
}


