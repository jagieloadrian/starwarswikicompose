package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.utils.Constants.FIRST_RATIONALE
import com.anjo.starwarswikicompose.utils.Constants.REQUEST_PERM
import com.anjo.starwarswikicompose.utils.Constants.SECOND_RATIONALE
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(AndroidJUnit4::class)
class PermissionRequestKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalPermissionsApi::class)
    @Test
    fun givenPermissionScreen_whenIsDeniedAndTrue_thenAssertComponents() {
        //given
        val permissionState = Mockito.mock(PermissionState::class.java)
        val denied = PermissionStatus.Denied(shouldShowRationale = true)
        permissionState.stub {
            on { status } doReturn denied
        }

        var result = ""
        val expected = "perm allow"
        composeTestRule.setContent {
            AccessPermissionBox(permissionState) { result = expected }
        }
        //when and then
        val text = composeTestRule.onNodeWithText(FIRST_RATIONALE)

        text.assertIsDisplayed()
        text.assertIsEnabled()

        val firstButton = composeTestRule.onNodeWithText(REQUEST_PERM)

        firstButton.assertIsDisplayed()
        firstButton.assertIsEnabled()
        firstButton.assertIsNotFocused()

        val secondButton = composeTestRule.onNodeWithText(REQUEST_PERM)

        secondButton.assertIsDisplayed()
        secondButton.assertIsEnabled()
        secondButton.assertIsNotFocused()

        secondButton.performClick()

        result shouldBe expected
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Test
    fun givenPermissionScreen_whenIsDeniedAndFalse_thenAssertComponents() {
        //given
        val permissionState = Mockito.mock(PermissionState::class.java)
        val denied = PermissionStatus.Denied(shouldShowRationale = false)
        permissionState.stub {
            on { status } doReturn denied
        }

        var result = ""
        val expected = "perm allow"
        composeTestRule.setContent {
            AccessPermissionBox(permissionState) { result = expected }
        }
        //when and then
        val text = composeTestRule.onNodeWithText(SECOND_RATIONALE)

        text.assertIsDisplayed()
        text.assertIsEnabled()

        val firstButton = composeTestRule.onNodeWithText(REQUEST_PERM)

        firstButton.assertIsDisplayed()
        firstButton.assertIsEnabled()
        firstButton.assertIsNotFocused()

        val secondButton = composeTestRule.onNodeWithText(REQUEST_PERM)

        secondButton.assertIsDisplayed()
        secondButton.assertIsEnabled()
        secondButton.assertIsNotFocused()

        secondButton.performClick()

        result shouldBe expected
    }
}