package com.anjo.starwarswikicompose.presentation.screens.common

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.presentation.common.InfoBox
import com.anjo.starwarswikicompose.presentation.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.utils.getLocalWidth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonFunctionsKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenInfoBox_whenDisplayed_thenAssertComponents() {
        //given

        composeTestRule.setContent {
            val thirdWidth = (getLocalWidth() / 3).dp
            val navController = rememberNavController()
            InfoBox("cornerName", "name", null, "objectId", FILMS, thirdWidth, navController)
        }

        //when and then
        val cornerName = composeTestRule.onNodeWithText("cornerName")
        cornerName.assertIsDisplayed()
        cornerName.assertIsNotFocused()

        val name = composeTestRule.onNodeWithText("name")
        name.assertIsDisplayed()
        name.assertIsNotFocused()
    }


    @Test
    fun givenInfoBoxColumn_whenDisplayed_thenAssertComponents() {
        //given

        composeTestRule.setContent {
            val thirdWidth = (getLocalWidth() / 3).dp
            InfoBoxColumn("cornerName", "name", listOf("firstThing", null, "secondThing"), thirdWidth)
        }

        //when and then
        val cornerName = composeTestRule.onNodeWithText("cornerName")
        cornerName.assertIsDisplayed()

        val name = composeTestRule.onNodeWithText("name")
        name.assertIsDisplayed()
    }

    @Test
    fun givenHorizontalBoxes_whenDisplayed_thenAssertComponents() {
        //given
        val connection = Connection(2, listOf(UniversalChunk(id = "vehicleId1", name = "vehicleName1"),
                UniversalChunk(id = "vehicleId2", name = "vehicleName2")))

        composeTestRule.setContent {
            val halfWidth = (getLocalWidth() / 2).dp
            val navController = rememberNavController()
            ShowHorizontalBoxes(connection, VEHICLES, halfWidth, navController)
        }

        //when and then
        val images = composeTestRule.onAllNodesWithTag("related_button_tag")
        images.assertCountEquals(2)

        val firstVehicle = composeTestRule.onNodeWithText(connection.objects[0].name)
        firstVehicle.assertIsDisplayed()
        firstVehicle.assertIsNotFocused()


        val secondVehicle = composeTestRule.onNodeWithText(connection.objects[1].name)
        secondVehicle.assertIsDisplayed()
        secondVehicle.assertIsNotFocused()
    }
}