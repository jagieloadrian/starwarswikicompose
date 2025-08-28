package com.anjo.starwarswikicompose.presentation.common.appbars

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onSiblings
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.MainViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomTopAppBarKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenTopAppBAr_whenMakeOps_thenAssertResults() = runTest {
        //given
        val mainViewModel = mockk<MainViewModel>()

        coEvery { mainViewModel.isNotificationsEnabled } returns MutableStateFlow(false).asStateFlow()
        coEvery { mainViewModel.changeStateOfMusic() } just Runs
        coEvery { mainViewModel.pauseMusic() } just Runs

        composeTestRule.setContent {
            val navController = rememberNavController()
            Scaffold(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) { contentPadding ->
                CustomTopAppBar(navHostController = navController, mainViewModel = mainViewModel,
                    modifier = Modifier.padding(contentPadding))
            }
        }

        val semanticSwitch = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch)

        composeTestRule.waitForIdle()
        composeTestRule.onRoot(true).printToLog("TOPAPPBAR")

        //when then
        val homeIcon = composeTestRule.onNodeWithContentDescription("home icon")
        homeIcon.assertIsNotFocused()
        homeIcon.assertIsDisplayed()
        homeIcon.assertIsEnabled()

        val mainText = composeTestRule.onNodeWithText("StarWarsWiki")
        mainText.assertIsDisplayed()
        mainText.assertIsEnabled()

        val optionIcon = composeTestRule.onNodeWithContentDescription("options")
        optionIcon.assertIsNotFocused()
        optionIcon.assertIsDisplayed()
        optionIcon.assertIsEnabled()

        optionIcon.performClick()

        val notes = composeTestRule.onNodeWithText("Notes")
        notes.assertIsNotFocused()
        notes.assertIsDisplayed()
        notes.assertIsEnabled()

        val notesIcon = composeTestRule.onNodeWithContentDescription("Notes")
        notesIcon.assertIsNotFocused()
        notesIcon.assertIsDisplayed()
        notesIcon.assertIsEnabled()

        val feedbackIcon = composeTestRule.onNodeWithContentDescription("Feedback")
        feedbackIcon.assertIsNotFocused()
        feedbackIcon.assertIsDisplayed()
        feedbackIcon.assertIsEnabled()

        val feedback = composeTestRule.onNodeWithText("Feedback")
        feedback.assertIsNotFocused()
        feedback.assertIsDisplayed()
        feedback.assertIsEnabled()

        val info = composeTestRule.onNodeWithText("Notes")
        info.assertIsNotFocused()
        info.assertIsDisplayed()
        info.assertIsEnabled()

        val infoIcon = composeTestRule.onNodeWithContentDescription("Notes")
        infoIcon.assertIsNotFocused()
        infoIcon.assertIsDisplayed()
        infoIcon.assertIsEnabled()

        val notification = composeTestRule.onNodeWithText("Notification", useUnmergedTree = true)
        notification.assertIsDisplayed()
        notification.assertIsEnabled()

        val notificationIcon = composeTestRule.onNodeWithContentDescription("Notification")
        notificationIcon.assertIsDisplayed()
        notificationIcon.assertIsEnabled()

        val notificationSwitch = notification.onSiblings().filterToOne(semanticSwitch)

        notificationSwitch.assertIsNotFocused()
        notificationSwitch.assertIsDisplayed()
        notificationSwitch.assertIsEnabled()
        notificationSwitch.assertIsToggleable().assertIsOff()

        val soundOn = composeTestRule.onNodeWithText("Sound", useUnmergedTree = true)
        soundOn.assertIsDisplayed()
        soundOn.assertIsEnabled()


        val soundOnIcon = composeTestRule.onNodeWithContentDescription("Sound", useUnmergedTree = true)
        soundOnIcon.assertIsDisplayed()
        soundOnIcon.assertIsEnabled()

        val soundOnSwitch = soundOn.onSiblings().filterToOne(semanticSwitch)

        soundOnSwitch.assertIsNotFocused()
        soundOnSwitch.assertIsDisplayed()
        soundOnSwitch.assertIsEnabled()
        soundOnSwitch.assertIsToggleable().assertIsOn()

        soundOnSwitch.performClick()

        soundOnSwitch.assertIsToggleable().assertIsOff()
    }
}