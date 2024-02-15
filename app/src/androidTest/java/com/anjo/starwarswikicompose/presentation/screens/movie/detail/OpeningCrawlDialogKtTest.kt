package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.utils.getLocalWidth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpeningCrawlDialogKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenArguments_whenOpenInfoBoxDialog_thenAssertOps() {
        //given
        composeTestRule.setContent {
            val width = getLocalWidth()
            val thirdWidth = (width / 3).dp
            val twoThirdsWidth = thirdWidth * 2
            InfoBoxDialog("CornerName", "longDescription", twoThirdsWidth)
        }
        //when and then
        val cornerName = composeTestRule.onNodeWithText("CornerName")
        cornerName.assertIsDisplayed()
        cornerName.assertIsEnabled()

        val longDesc = composeTestRule.onNodeWithText("longDescription")
        longDesc.assertIsDisplayed()
        longDesc.assertIsEnabled()

        longDesc.onParent().performClick()

        val dialog = composeTestRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.IsDialog))

        dialog.assertIsDisplayed()
        dialog.assertIsEnabled()

        val button = composeTestRule.onNodeWithText("Close")

        button.performClick()

        dialog.isNotDisplayed()
    }
}