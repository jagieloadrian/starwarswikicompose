package com.anjo.starwarswikicompose.presentation.common.loading

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anjo.starwarswikicompose.utils.TestTags.SHIMMER_EFFECT_TAG
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShimmerEffectKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenShimmerEffect_whenDisplay_thenAssertComponents() {
        //given
        composeTestRule.setContent {
            ShimmerEffect()
        }
        //when  //then
        val childs = composeTestRule.onNodeWithTag(SHIMMER_EFFECT_TAG).onChildren()

        childs.assertCountEquals(3)
    }
}