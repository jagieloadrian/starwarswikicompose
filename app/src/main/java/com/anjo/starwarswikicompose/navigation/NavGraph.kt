package com.anjo.starwarswikicompose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anjo.starwarswikicompose.presentation.screens.home.HomeScreen
import com.anjo.starwarswikicompose.presentation.screens.images.ImageScreen
import com.anjo.starwarswikicompose.presentation.screens.movie.detail.MovieContentScreen
import com.anjo.starwarswikicompose.presentation.screens.person.detail.PersonContentScreen
import com.anjo.starwarswikicompose.presentation.screens.planet.detail.PlanetContentScreen
import com.anjo.starwarswikicompose.presentation.screens.specie.detail.SpecieContentScreen
import com.anjo.starwarswikicompose.presentation.screens.starship.detail.StarshipContentScreen
import com.anjo.starwarswikicompose.presentation.screens.vehicle.detail.VehicleContentScreen
import com.anjo.starwarswikicompose.presentation.screens.webview.WebViewScreen
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
        composable(route = Screen.Home.route,
                enterTransition = SlideEnterAnimation(Right),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            HomeScreen(navController)
        }
        composable(route = Screen.PersonDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_PERSON_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                ),
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            PersonContentScreen(navController)
        }
        composable(route = Screen.MovieDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_MOVIE_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                ),
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            MovieContentScreen(navController)
        }
        composable(route = Screen.PlanetDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_PLANET_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                ),
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            PlanetContentScreen(navController)
        }
        composable(route = Screen.SpecieDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_SPECIE_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                ),
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            SpecieContentScreen(navController)
        }
        composable(route = Screen.StarshipDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_STARSHIP_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                ),
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            StarshipContentScreen(navController)
        }
        composable(route = Screen.VehicleDetail.route,
                arguments = listOf(
                        navArgument(DETAILS_VEHICLE_ARGUMENT_KEY) {
                            type = NavType.StringType
                        }
                ),
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            VehicleContentScreen(navController)
        }
        composable(route = Screen.ImageSearch.route,
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            ImageScreen(navController)
        }
        composable(route = Screen.WookiepediaWebView.route,
                enterTransition = SlideEnterAnimation(Left),
                exitTransition = SlideExitAnimation(Left),
                popEnterTransition = SlideEnterAnimation(Right),
                popExitTransition = SlideExitAnimation(Right)) {
            WebViewScreen(navController)
        }
    }
}

private fun SlideEnterAnimation(
        towards: AnimatedContentTransitionScope.SlideDirection,
): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
        slideIntoContainer(
                towards = towards,
                animationSpec = tween(1200)
        )
    }
}

private fun SlideExitAnimation(
        towards: AnimatedContentTransitionScope.SlideDirection,
): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
        slideOutOfContainer(
                towards = towards,
                animationSpec = tween(1200)
        )
    }
}