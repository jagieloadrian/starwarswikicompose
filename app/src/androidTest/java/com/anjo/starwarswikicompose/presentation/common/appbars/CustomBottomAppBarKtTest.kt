package com.anjo.starwarswikicompose.presentation.common.appbars

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomBottomAppBarKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenBottomAppBar_whenMakeOps_thenAssertTabs() {
        //given
        composeTestRule.setContent {
            val navController = rememberNavController()
            CustomBottomAppBar(navController)
        }

        //when and then
        val wikiButtonTab = composeTestRule.onNodeWithText("WIKI")

        wikiButtonTab.assertIsNotFocused()
        wikiButtonTab.assertIsDisplayed()
        wikiButtonTab.assertIsEnabled()

        val imageButtonTab = composeTestRule.onNodeWithText("IMAGES")

        imageButtonTab.assertIsNotFocused()
        imageButtonTab.assertIsDisplayed()
        imageButtonTab.assertIsEnabled()

        val moreButtonTab = composeTestRule.onNodeWithText("MORE")

        moreButtonTab.assertIsNotFocused()
        moreButtonTab.assertIsDisplayed()
        moreButtonTab.assertIsEnabled()
    }
}