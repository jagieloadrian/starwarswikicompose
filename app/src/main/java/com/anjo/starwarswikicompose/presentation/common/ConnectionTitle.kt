package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.TestTags.CONNECTION_TITLE_TAG

@Composable
fun ConnectionTitle(modifier: Modifier = Modifier, text: String, shouldShowTitle: Boolean) {
    if (shouldShowTitle) {
        Text(text = text,
                fontFamily = SOLOFontName,
                modifier = modifier
                        .fillMaxWidth()
                        .height(HOME_ICON_HEIGHT)
                        .testTag(CONNECTION_TITLE_TAG),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
        )
    }
}