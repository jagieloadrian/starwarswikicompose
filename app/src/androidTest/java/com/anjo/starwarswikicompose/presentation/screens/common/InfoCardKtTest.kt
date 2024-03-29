package com.anjo.starwarswikicompose.presentation.screens.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.BuildConfig.VERSION_CODE
import com.anjo.starwarswikicompose.presentation.common.InfoDialog
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InfoCardKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenInfoDialogScreen_whenDisplay_thenVerifyComponents() {
        //given
        var result = ""
        val expected = "Dissmiss clicked"
        composeTestRule.setContent {
            InfoDialog { result = expected }
        }
        //when and then
        val appName = composeTestRule.onNodeWithText("Star Wars Wiki")

        appName.assertIsDisplayed()
        appName.assertIsEnabled()

        val versionLabel = composeTestRule.onNodeWithText("Version name: ")
        versionLabel.assertIsDisplayed()
        versionLabel.assertIsEnabled()

        val versionValue = composeTestRule.onNodeWithText("1.0")
        versionValue.assertIsDisplayed()
        versionValue.assertIsEnabled()

        val versionCodeLabel = composeTestRule.onNodeWithText("Version code: ")
        versionCodeLabel.assertIsDisplayed()
        versionCodeLabel.assertIsEnabled()

        val versionCodeValue = composeTestRule.onNodeWithText(VERSION_CODE.toString())
        versionCodeValue.assertIsDisplayed()
        versionCodeValue.assertIsEnabled()

        val isDebugModeLabel = composeTestRule.onNodeWithText("Is in debug mode: ")
        isDebugModeLabel.assertIsDisplayed()
        isDebugModeLabel.assertIsEnabled()

        val isDebugModeValue = composeTestRule.onNodeWithText("true")
        isDebugModeValue.assertIsDisplayed()
        isDebugModeValue.assertIsEnabled()

        val copyright = composeTestRule.onNodeWithText("Copyright © 2024, d18")
        copyright.assertIsDisplayed()
        copyright.assertIsEnabled()

        val button = composeTestRule.onNodeWithText("Close")

        button.assertIsEnabled()
        button.assertIsDisplayed()

        button.performClick()

        result shouldBe expected
    }
}