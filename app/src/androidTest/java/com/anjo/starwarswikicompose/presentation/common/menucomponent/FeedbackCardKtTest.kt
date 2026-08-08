package com.anjo.starwarswikicompose.presentation.common.menucomponent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.presentation.common.menucomponents.FeedbackCard
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedbackCardKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenFeedbackCard_whenDisplayed_thenVerifyComponents() {
        //given
        var result = ""
        val expected = "Dissmiss clicked"
        composeTestRule.setContent {
            FeedbackCard { result = expected }
        }
        //when and then
        val subject = composeTestRule.onNodeWithText("Subject")

        subject.assertIsDisplayed()
        subject.assertIsEnabled()
        subject.assertIsNotFocused()
        subject.performClick()
        subject.assertIsFocused()

        subject.performTextClearance()
        subject.performTextInput("newText")

        val description = composeTestRule.onNodeWithText("Description")

        description.assertIsDisplayed()
        description.assertIsEnabled()
        description.assertIsNotFocused()
        description.performClick()
        description.assertIsFocused()

        description.performTextClearance()
        description.performTextInput("newText")

        val sendButton = composeTestRule.onNodeWithText("Send")
        sendButton.assertIsDisplayed()
        sendButton.assertIsEnabled()
        sendButton.assertIsNotFocused()

        val button = composeTestRule.onNodeWithText("Close")
        button.assertIsDisplayed()
        button.assertIsEnabled()
        button.assertIsNotFocused()

        button.performClick()

        result shouldBe expected
    }
}