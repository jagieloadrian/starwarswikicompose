package com.anjo.starwarswikicompose.presentation.common.errorempty

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onSibling
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.sw.Category
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptyScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenEmptyScreen_whenIsDisplayed_thenAssertElements() {
        //given
        composeTestRule.setContent {
            EmptyScreen(Category.FILMS, "images")
        }
        //when and then
        val image = composeTestRule.onNodeWithContentDescription("network error icon")

        image.assertIsDisplayed()

        val text = image.onSibling()
        text.assertIsDisplayed()
    }
}