package com.anjo.starwarswikicompose.testutils

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

infix fun NavHostController.assertCurrentRouteName(expectedRouteName: String) {
    currentBackStackEntry?.destination?.route shouldBe expectedRouteName
}

infix fun ConnectionDto.connectionShouldBeSame(expectedConnection: ConnectionDto) {
    this.totalCount shouldBe expectedConnection.totalCount
    this.objects shouldContainAll expectedConnection.objects
}

fun assertStringField(stringField: SemanticsNodeInteraction, textInput: String) {
    stringField.assertIsDisplayed()
    stringField.assertIsEnabled()
    stringField.assertHasClickAction()
    stringField.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsEditable, true))
    stringField.assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Next))
    stringField.assertIsNotFocused()

    stringField.performTextClearance()
    stringField.performTextInput(textInput)
}

fun assertListField(listField: SemanticsNodeInteraction, textInput: List<String>) {
    val textBox = listField.onChildAt(0)

    textBox.assertIsDisplayed()
    textBox.assertIsEnabled()
    textBox.assertHasClickAction()
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsEditable, true))
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Next))
    textBox.assertIsNotFocused()


    val addButton = listField.onChildAt(1)
    addButton.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    addButton.assertIsNotEnabled()

    val addedElements = listField.onChildAt(2)
    addedElements.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))

    textInput.forEach {
        textBox.performTextClearance()
        textBox.performTextInput(it)
        addButton.performClick()
    }

    addedElements.onChildren().assertCountEquals(textInput.size)
}

fun assertChunksField(composeRule: ComposeContentTestRule, chunkField: SemanticsNodeInteraction,
                      textInput: List<String>) {
    val textBox = chunkField.onChildAt(0)
    textBox.assertIsDisplayed()
    textBox.assertIsEnabled()
    textBox.assertTextEquals("")
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.DropdownList))
    textBox.assertHasClickAction()
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsEditable, true))
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Next))
    textBox.assertIsNotFocused()

    val addButton = chunkField.onChildAt(1)
    addButton.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    addButton.assertIsNotEnabled()

    val addedElements = chunkField.onChildAt(2)
    addedElements.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsTraversalGroup, true))

    textInput.forEach {
        textBox.performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText(it).performClick()
        composeRule.waitForIdle()
        addButton.performClick()
    }
    composeRule.waitForIdle()
    addedElements.onChildren().assertCountEquals(textInput.size)
}

fun assertChunkField(composeRule: ComposeContentTestRule, chunkField: SemanticsNodeInteraction,
                     input: UniversalChunkDto) {
    val textBox = chunkField.onChildAt(0)
    textBox.assertIsDisplayed()
    textBox.assertIsEnabled()
    textBox.assertTextEquals("")
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.DropdownList))
    textBox.assertHasClickAction()
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.IsEditable, true))
    textBox.assert(SemanticsMatcher.expectValue(SemanticsProperties.ImeAction, ImeAction.Next))
    textBox.assertIsNotFocused()

    val addButton = chunkField.onChildAt(1)
    addButton.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    addButton.assertHasClickAction()

    textBox.performClick()
    composeRule.waitForIdle()
    composeRule.onNodeWithText(input.name).performClick()
    composeRule.waitForIdle()
    addButton.performClick()

    textBox.assertTextEquals(input.name)
}

fun assertRelatedBoxes(composeRule: ComposeContentTestRule, connectionDto: ConnectionDto) {
    connectionDto.objects.forEach { connection ->
        val image = composeRule.onNodeWithContentDescription("RelatedBox ${connection.name}", useUnmergedTree = true)
        image.performScrollTo()
        image.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
        image.assertIsDisplayed()

        image.onSibling().assertIsDisplayed()

        val imageParent = image.onParent()
        imageParent.assert(SemanticsMatcher.expectValue(SemanticsProperties.Shape, RectangleShape))

        imageParent.onParent().assertHasClickAction()
        imageParent.onParent().assertIsNotFocused()
    }
}

fun assertIncludedImage(composeTestRule: ComposeContentTestRule) {
    val image = composeTestRule.onNodeWithContentDescription("image from flickr",
            substring = true,
            ignoreCase = true,
            useUnmergedTree = true)
    image.assertIsDisplayed()
    image.assertIsEnabled()

    val onClickLeft = composeTestRule.onNodeWithContentDescription("onClickLeft", useUnmergedTree = true)
    onClickLeft.assertIsDisplayed()
    onClickLeft.assertIsEnabled()
    val onClickLeftParent = onClickLeft.onParent()

    onClickLeftParent.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Shape, RectangleShape))
    onClickLeftParent.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    onClickLeftParent.onParent().assertIsNotFocused()

    val onClickRight = composeTestRule.onNodeWithContentDescription("onCLickRight", useUnmergedTree = true)
    onClickRight.assertIsDisplayed()
    onClickRight.assertIsEnabled()

    val onClickRightParent = onClickRight.onParent()
    onClickRightParent.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Shape, RectangleShape))
    onClickRightParent.onParent().assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    onClickRightParent.onParent().assertIsNotFocused()
}
