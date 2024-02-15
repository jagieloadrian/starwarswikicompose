package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onSibling
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenErrorScreen_whenDisplay_thenAssertElements() {
        //given
        val excMessage = "404 Not Found"

        composeTestRule.setContent {
            ErrorScreen(excMessage)
        }

        //when and then
        val image = composeTestRule.onNodeWithContentDescription("network error icon")
        image.assertIsEnabled()
        image.assertIsDisplayed()

        val label = image.onSibling()

        label.assertIsDisplayed()
        label.assertIsEnabled()

        label.assertTextEquals(excMessage)
    }
}