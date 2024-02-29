package com.anjo.starwarswikicompose.presentation.screens.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.presentation.common.AddImageFab
import com.anjo.starwarswikicompose.utils.Constants.FAB_BUTTON_TAG
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddImageFabKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenFabAndExtendedAsFalse_whenIsDisplayed_thenAssertComponents() {
        //given
        var actual = ""
        val expected = "it was clicked"

        composeTestRule.setContent {
            AddImageFab(modifier = Modifier, extended = false) { actual = expected }
        }
        //when and then
        val button = composeTestRule.onNodeWithContentDescription(FAB_BUTTON_TAG)
        button.isDisplayed()
        button.assertIsNotFocused()
        button.performClick()

        actual shouldBe expected
    }


    @Test
    fun givenFabAndExtendedAsTrue_whenIsDisplayed_thenAssertComponents() {
        //given
        var actual = ""
        val expected = "it was clicked"

        composeTestRule.setContent {
            AddImageFab(modifier = Modifier, extended = true) { actual = expected }
        }

        //when and then
        val buttonText = composeTestRule.onNodeWithText("Add Image from Clipboard")

        buttonText.assertIsDisplayed()
        buttonText.assertIsNotFocused()

        val button = composeTestRule.onNodeWithContentDescription(FAB_BUTTON_TAG)
        button.isDisplayed()
        button.assertIsNotFocused()
        button.performClick()

        actual shouldBe expected
    }

}