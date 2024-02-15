package com.anjo.starwarswikicompose.presentation.screens.notes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.NoteModel
import com.anjo.starwarswikicompose.utils.getLocalHeight
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CardNoteKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCardNote_whenMakeOps_thenAsserResults() {
        //given
        var resultString = ""
        val newTextChange = "newTextChange"

        val noteModel = NoteModel(1, "new default text")
        val initState = mutableStateOf(false)
        val userText = mutableStateOf(noteModel.text)

        composeTestRule.setContent {
            CardNoteDialog(((getLocalHeight() / 3) * 2).dp, initState, userText) { resultString = userText.value }
        }

        //when and then
        val userTextField = composeTestRule.onNodeWithText("Your notes")

        userTextField.assertIsDisplayed()
        userTextField.assertIsNotFocused()

        userTextField.performTextClearance()
        userTextField.performTextInput(newTextChange)

        val button = composeTestRule.onNodeWithText("Save & Close")
        button.assertIsDisplayed()
        button.assertIsNotFocused()

        button.performClick()

        resultString shouldBe newTextChange
    }
}