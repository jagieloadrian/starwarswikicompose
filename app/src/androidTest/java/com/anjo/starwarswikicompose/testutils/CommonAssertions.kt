package com.anjo.starwarswikicompose.testutils

import androidx.navigation.NavHostController
import io.kotest.matchers.shouldBe


infix fun NavHostController.assertCurrentRouteName(expectedRouteName: String) {
    currentBackStackEntry?.destination?.route shouldBe expectedRouteName
}