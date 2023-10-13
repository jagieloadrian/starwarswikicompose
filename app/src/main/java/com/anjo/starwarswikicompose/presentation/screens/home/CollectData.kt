package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.utils.Category


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