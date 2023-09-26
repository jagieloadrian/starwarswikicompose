package com.anjo.starwarswikicompose.navigation

import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Category.PEOPLES

sealed class Screen(val route: String, val enum:Category?) {
    object Welcome : Screen("welcome_screen", null)
    object Home : Screen("home_screen", null)
    object People:Screen("people_screen", PEOPLES)
}