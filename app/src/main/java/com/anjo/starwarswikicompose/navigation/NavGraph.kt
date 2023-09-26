package com.anjo.starwarswikicompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anjo.starwarswikicompose.presentation.screens.home.HomeScreen
import com.anjo.starwarswikicompose.presentation.screens.welcome.WelcomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
            navController = navController,
            startDestination = startDestination
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}