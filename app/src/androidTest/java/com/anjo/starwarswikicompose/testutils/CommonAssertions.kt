package com.anjo.starwarswikicompose.testutils

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import io.kotest.matchers.shouldBe


infix fun NavHostController.assertCurrentRouteName(expectedRouteName: String) {
    currentBackStackEntry?.destination?.route shouldBe expectedRouteName
}

infix fun UniversalChunk.assertIsVisibleWith(composeTestRule: ComposeContentTestRule) {
    val name = composeTestRule.onNodeWithText(this.name)
    name.assertIsDisplayed()
    name.assertIsEnabled()

    val desc = composeTestRule.onNodeWithText(this.desc)
    desc.assertIsDisplayed()
    desc.assertIsEnabled()

    desc.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))
}