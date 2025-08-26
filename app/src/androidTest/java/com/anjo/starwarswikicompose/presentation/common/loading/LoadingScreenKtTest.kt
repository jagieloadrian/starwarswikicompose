package com.anjo.starwarswikicompose.presentation.common.loading

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.utils.TestTags.STORMTROOPER_DRAW
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `when show loading screen then assert view`() {
        //given and when
        composeTestRule.setContent {
            LoadingScreen()
        }
        //then
        val draw = composeTestRule.onNodeWithContentDescription(STORMTROOPER_DRAW)

        draw.assertIsDisplayed()
        draw.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
    }
}