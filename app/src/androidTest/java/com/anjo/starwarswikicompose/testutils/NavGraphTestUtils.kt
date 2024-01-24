package com.anjo.starwarswikicompose.testutils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.screens.home.HomeScreen
import com.anjo.starwarswikicompose.presentation.screens.welcome.WelcomeScreen
import com.anjo.starwarswikicompose.presentation.screens.welcome.WelcomeViewModel

@Composable
fun WelcomeTestNavGraph(navHostController: NavHostController, starDestination:String, welcomeViewModel: WelcomeViewModel) {
    NavHost(navHostController, starDestination, Modifier) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController, welcomeViewModel)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController)
        }
    }
}