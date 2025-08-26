package com.anjo.starwarswikicompose.presentation.screens.webview

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebViewKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `given url when webview then should create emtpy compose`() {
        //given
        val url = "http://localhost:8080"

        //when
        composeTestRule.setContent {
            WebView(url = url)
        }
        //then
        composeTestRule.onRoot(false).onChild().assertExists()
    }
}