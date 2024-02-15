package com.anjo.starwarswikicompose.presentation.screens.vehicle.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.testutils.assertIsVisibleWith
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleHomeComposablesKtTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenVehicle_whenDisplayed_thenAssertOps() {
        //given
        val vehicles = listOf(UniversalChunk("1", "name1", "desc1"),
                UniversalChunk("2", "name2", "desc2"),
                UniversalChunk("3", "name3", "desc3"))
        val vehicleState = HomeVehicleViewModel.VehicleState(vehicles)
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeVehicleContent(vehicleState, navController)
        }

        //when and then
        vehicles.forEach { chunk -> chunk assertIsVisibleWith composeTestRule }
    }

    @Test
    fun givenEmptyVehicleAndLoading_whenDisplayed_thenAssertOps() {
        //given
        val vehicleState = HomeVehicleViewModel.VehicleState(listOf(), true)
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeVehicleContent(vehicleState, navController)
        }

        //when and then
        composeTestRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.VerticalScrollAxisRange))
                .onChildren().assertCountEquals(3)
    }
}