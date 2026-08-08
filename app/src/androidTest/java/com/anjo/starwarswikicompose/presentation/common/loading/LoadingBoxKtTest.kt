package com.anjo.starwarswikicompose.presentation.common.loading

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.utils.TestTags.PROGRESS_INDICATOR_TAG
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingBoxKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenLoadingBox_whenDisplayed_thenAssertComponents() {
        //given
        composeTestRule.setContent {
            LoadingBox()
        }
        //when and then

        val indicator = composeTestRule.onNodeWithTag(PROGRESS_INDICATOR_TAG)

        indicator.assertIsEnabled()
        indicator.assertIsDisplayed()
    }
}