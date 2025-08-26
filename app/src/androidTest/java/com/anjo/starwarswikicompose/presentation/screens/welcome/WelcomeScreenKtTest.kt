package com.anjo.starwarswikicompose.presentation.screens.welcome

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.testutils.TestConstants.ONBOARD_IMAGE_DESCRIPTION
import com.anjo.starwarswikicompose.testutils.WelcomeTestNavGraph
import com.anjo.starwarswikicompose.testutils.assertCurrentRouteName
import com.anjo.starwarswikicompose.utils.Constants.GO_TO_APP
import com.anjo.starwarswikicompose.utils.OnboardingPage
import com.anjo.starwarswikicompose.utils.TestTags.WELCOME_BUTTON_TAG
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navHostController: NavHostController

    @Test
    fun addNavHost_whenWelcomeScreen_thenScreenOpen() {
        //given
        val welcomeViewModel = mockk<WelcomeViewModel>()

        composeTestRule.setContent {
            navHostController = rememberNavController()
            WelcomeTestNavGraph(navHostController, Screen.Welcome.route, welcomeViewModel)
        }
        //when and then
        navHostController assertCurrentRouteName Screen.Welcome.route

        //First page
        val image = composeTestRule.onNodeWithContentDescription(ONBOARD_IMAGE_DESCRIPTION + 0)
        image.assertIsDisplayed()
        image.assertIsEnabled()

        val firstTitle = composeTestRule.onNodeWithText(OnboardingPage.First.title)
        firstTitle.assertIsDisplayed()
        firstTitle.assertTextEquals(OnboardingPage.First.title)

        val firstDesc = composeTestRule.onNodeWithText(OnboardingPage.First.description)
        firstDesc.assertIsDisplayed()
        firstDesc.assertTextEquals(OnboardingPage.First.description)

        image.performTouchInput { swipeLeft() }

        //Second page
        val secondImage = composeTestRule.onNodeWithContentDescription(ONBOARD_IMAGE_DESCRIPTION + 1)
        secondImage.assertIsDisplayed()
        secondImage.assertIsEnabled()

        val secondTitle = composeTestRule.onNodeWithText(OnboardingPage.Second.title)
        secondTitle.assertIsDisplayed()
        secondTitle.assertTextEquals(OnboardingPage.Second.title)

        val secondDesc = composeTestRule.onNodeWithText(OnboardingPage.Second.description)
        secondDesc.assertIsDisplayed()
        secondDesc.assertTextEquals(OnboardingPage.Second.description)

        secondImage.performTouchInput { swipeLeft() }

        //Second page
        val thirdImage = composeTestRule.onNodeWithContentDescription(ONBOARD_IMAGE_DESCRIPTION + 2)
        thirdImage.assertIsDisplayed()
        thirdImage.assertIsEnabled()

        val thirdTitle = composeTestRule.onNodeWithText(OnboardingPage.Third.title)
        thirdTitle.assertIsDisplayed()
        thirdTitle.assertTextEquals(OnboardingPage.Third.title)

        val thirdDesc = composeTestRule.onNodeWithText(OnboardingPage.Third.description)
        thirdDesc.assertIsDisplayed()
        thirdDesc.assertTextEquals(OnboardingPage.Third.description)

        val button = composeTestRule.onNodeWithTag(WELCOME_BUTTON_TAG)
        button.assertIsDisplayed()
        button.assertTextEquals(GO_TO_APP)
    }
}