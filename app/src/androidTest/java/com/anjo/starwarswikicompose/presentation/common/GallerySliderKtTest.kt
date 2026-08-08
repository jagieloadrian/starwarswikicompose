package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GallerySliderKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenListOfImages_whenMakeOps_thenAssertResults() {

        val images = listOf(ImageSliderModel(1, "objectId1", "someUrl1"),
                ImageSliderModel(2, "objectId2", "someUrl2"),
                ImageSliderModel(3, "objectId3", "someUrl3"))

        var firstClick = ""
        var rightClick = ""
        //given
        composeTestRule.setContent {
            GallerySlider(images, onCLickLeft = { firstClick = "newName" }, onCLickRight = { rightClick = it.objectId })
        }
        //when and then
        val image1 = composeTestRule.onNodeWithContentDescription("image from flickr objectId1")

        image1.assertIsEnabled()
        image1.isDisplayed()

        val leftButton = composeTestRule.onNodeWithContentDescription("onClickLeft")

        leftButton.isDisplayed()
        leftButton.assertIsEnabled()

        leftButton.performClick()

        firstClick shouldBe "newName"
        composeTestRule.mainClock.advanceTimeBy(2000)
        val rightButton = composeTestRule.onNodeWithContentDescription("onCLickRight")

        rightButton.isDisplayed()
        rightButton.assertIsEnabled()

        rightButton.performClick()
        composeTestRule.mainClock.advanceTimeBy(2000)
        rightClick shouldBe images[0].objectId

        image1.performTouchInput { swipeLeft() }

        val image2 = composeTestRule.onNodeWithContentDescription("image from flickr objectId2")

        image2.assertIsEnabled()
        image2.isDisplayed()
    }
}