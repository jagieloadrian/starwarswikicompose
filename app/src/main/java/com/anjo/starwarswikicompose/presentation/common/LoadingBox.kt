package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.ui.theme.LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.utils.Constants.PROGRESS_INDICATOR_TAG

@Composable
fun LoadingBox() {
    Card(modifier = Modifier
            .fillMaxWidth()
            .height(PICTURE_HEIGHT)
            .padding(MEDIUM_PADDING),
            shape = RoundedCornerShape(MEDIUM_PADDING),
            backgroundColor = Color.Transparent) {
        Box(
                modifier = Modifier.fillMaxWidth()
                        .height(PICTURE_HEIGHT / 2)
                        .padding(MEDIUM_PADDING)
                        .background(Color.Transparent),
                contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize(fraction = 0.5f)
                    .padding(LARGE_PADDING)
                    .testTag(PROGRESS_INDICATOR_TAG),
                    color = MaterialTheme.colors.mainBackgroundColors,
                    backgroundColor = Color.Transparent,
                    strokeCap = StrokeCap.Round)
        }
    }
}