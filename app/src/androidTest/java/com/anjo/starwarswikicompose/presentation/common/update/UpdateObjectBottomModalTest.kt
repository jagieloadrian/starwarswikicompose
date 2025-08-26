package com.anjo.starwarswikicompose.presentation.common.update

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_MAIN_SCREEN
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpdateObjectBottomModalTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenContent_whenDisplay_thenVerifyComponents() {
        //given

        var result = 0

        composeTestRule.setContent {
            CopyModelBottomModal(
                    modifier = Modifier,
                    { result = 1 }
            ) {
                Text("TEST")
            }
        }

        //when and then
        val modal = composeTestRule.onNodeWithTag(ADD_OBJECT_MAIN_SCREEN, useUnmergedTree = true)
        modal.assertExists()
        modal.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsContainer, true))
        modal.assert(SemanticsMatcher.expectValue(SemanticsProperties.PaneTitle, "Bottom Sheet"))

        val dragPart = modal.onChildren()[0].onChild()
        dragPart.assertExists()
        dragPart.assertIsNotFocused()

        val text = modal.onChildren()[1]

        text.assertExists()
        text.assertTextEquals("TEST")

        dragPart.performTouchInput { swipeDown() }

        composeTestRule.waitForIdle()

        result shouldBe 1
    }
}