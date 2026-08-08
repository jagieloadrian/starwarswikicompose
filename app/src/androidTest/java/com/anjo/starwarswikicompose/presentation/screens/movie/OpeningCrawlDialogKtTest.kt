package com.anjo.starwarswikicompose.presentation.screens.movie

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalWidth
import com.anjo.starwarswikicompose.utils.TestTags.INFO_BOX_DIALOG_TAG
import com.anjo.starwarswikicompose.utils.TestTags.OPENING_CRAWL_DIALOG_TAG
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

        composeTestRule.onNodeWithTag(INFO_BOX_DIALOG_TAG).performClick()

        val dialog = composeTestRule.onNodeWithTag(OPENING_CRAWL_DIALOG_TAG)

        dialog.assertIsDisplayed()
        dialog.assertIsEnabled()

        val button = composeTestRule.onNodeWithText("Close")

        button.performClick()

        dialog.isNotDisplayed()
    }
}