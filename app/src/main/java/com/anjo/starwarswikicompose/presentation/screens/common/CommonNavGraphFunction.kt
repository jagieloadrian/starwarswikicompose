package com.anjo.starwarswikicompose.presentation.screens.common

import androidx.navigation.NavHostController
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.utils.Category

fun <T> navigateToProperlyCompose(navController: NavHostController, item: T, category: Category) {
    when (category) {
        Category.FILMS     -> {
            val currentItem = item as GetAllFilmsQuery.Film
            navController.navigate(Screen.MovieDetail.passMovieId(currentItem.id))
            return
        }

        Category.PEOPLE    -> {
            val currentItem = item as GetAllPeoplesQuery.Person
            navController.navigate(Screen.PersonDetail.passPersonId(currentItem.id))
            return
        }

        Category.PLANETS   -> {
            val currentItem = item as GetAllPlanetsQuery.Planet
            navController.navigate(Screen.PlanetDetail.passPlanetId(currentItem.id))
            return
        }

        Category.SPECIES   -> {
            val currentItem = item as GetAllSpeciesQuery.Species
            navController.navigate(Screen.SpecieDetail.passSpecieId(currentItem.id))
            return
        }

        Category.STARSHIPS -> {
            val currentItem = item as GetAllStarshipsQuery.Starship
            navController.navigate(Screen.StarshipDetail.passStarshipId(currentItem.id))
            return
        }

        Category.VEHICLES  -> {
            val currentItem = item as GetAllVehiclesQuery.Vehicle
            navController.navigate(Screen.VehicleDetail.passVehicleId(currentItem.id))
            return
        }
    }
}

fun navigateToProperlyCompose(navController: NavHostController, itemId: String, category: Category) {
    when (category) {
        Category.FILMS     -> {
            navController.navigate(Screen.MovieDetail.passMovieId(itemId))
            return
        }

        Category.PEOPLE    -> {
            navController.navigate(Screen.PersonDetail.passPersonId(itemId))
            return
        }

        Category.PLANETS   -> {
            navController.navigate(Screen.PlanetDetail.passPlanetId(itemId))
            return
        }

        Category.SPECIES   -> {
            navController.navigate(Screen.SpecieDetail.passSpecieId(itemId))
            return
        }

        Category.STARSHIPS -> {
            navController.navigate(Screen.StarshipDetail.passStarshipId(itemId))
            return
        }

        Category.VEHICLES  -> {
            navController.navigate(Screen.VehicleDetail.passVehicleId(itemId))
            return
        }
    }
}