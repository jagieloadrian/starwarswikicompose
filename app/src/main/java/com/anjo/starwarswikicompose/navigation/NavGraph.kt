package com.anjo.starwarswikicompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anjo.starwarswikicompose.presentation.screens.details.movie.MovieContentScreen
import com.anjo.starwarswikicompose.presentation.screens.details.person.PersonContentScreen
import com.anjo.starwarswikicompose.presentation.screens.details.planet.PlanetContentScreen
import com.anjo.starwarswikicompose.presentation.screens.details.specie.SpecieContentScreen
import com.anjo.starwarswikicompose.presentation.screens.details.starship.StarshipContentScreen
import com.anjo.starwarswikicompose.presentation.screens.details.vehicle.VehicleContentScreen
import com.anjo.starwarswikicompose.presentation.screens.home.HomeScreen
import com.anjo.starwarswikicompose.presentation.screens.welcome.WelcomeScreen
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_MOVIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PERSON_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_PLANET_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_SPECIE_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_STARSHIP_ARGUMENT_KEY
import com.anjo.starwarswikicompose.utils.Constants.DETAILS_VEHICLE_ARGUMENT_KEY

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String, modifier: Modifier) {
    NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.PersonDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_PERSON_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                )) {
            PersonContentScreen(navController)
        }
        composable(route = Screen.MovieDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_MOVIE_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                )) {
            MovieContentScreen(navController)
        }
        composable(route = Screen.PlanetDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_PLANET_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                )) {
            PlanetContentScreen(navController)
        }
        composable(route = Screen.SpecieDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_SPECIE_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                )) {
            SpecieContentScreen(navController)
        }
        composable(route = Screen.StarshipDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_STARSHIP_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                )) {
            StarshipContentScreen(navController)
        }
        composable(route = Screen.VehicleDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_VEHICLE_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                )) {
            VehicleContentScreen(navController)
        }
    }
}