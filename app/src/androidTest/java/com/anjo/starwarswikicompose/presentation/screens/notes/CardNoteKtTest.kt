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
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalHeight
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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
        var dissmissActionResult = 0

        composeTestRule.setContent {
            CardNoteDialog(((getLocalHeight() / 3) * 2).dp, initState, userText,
                    saveAction = { resultString = userText.value }) { dissmissActionResult++ }
        }

        //when and then
        val userTextField = composeTestRule.onNodeWithText("Your notes")

        userTextField.assertIsDisplayed()
        userTextField.assertIsNotFocused()

        userTextField.performTextClearance()
        userTextField.performTextInput(newTextChange)

        val closeButton = composeTestRule.onNodeWithText("Close")
        closeButton.assertIsDisplayed()
        closeButton.assertIsNotFocused()

        val saveButton = composeTestRule.onNodeWithText("Save")
        saveButton.assertIsDisplayed()
        saveButton.assertIsNotFocused()

        saveButton.performClick()

        resultString shouldBe newTextChange

        closeButton.performClick()

        dissmissActionResult shouldNotBe 0
    }
}