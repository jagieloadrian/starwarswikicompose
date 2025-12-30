package com.anjo.starwarswikicompose.presentation.common.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.ui.theme.EXTRA_LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.LOADING_BOX_TAG
import com.anjo.starwarswikicompose.utils.TestTags.PROGRESS_INDICATOR_TAG

@Composable
fun LoadingBox() {
    Card(modifier = Modifier
            .fillMaxWidth()
            .height(PICTURE_HEIGHT)
            .padding(MEDIUM_PADDING)
        .testTag(LOADING_BOX_TAG),
            shape = RoundedCornerShape(MEDIUM_PADDING),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(PICTURE_HEIGHT / 2)
                        .padding(MEDIUM_PADDING)
                        .background(Color.Transparent),
                contentAlignment = Alignment.TopCenter
        ) {
            CircularProgressIndicator(modifier = Modifier
                    .size(EXTRA_LARGE_PADDING)
                    .testTag(PROGRESS_INDICATOR_TAG),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Transparent,
                    strokeCap = StrokeCap.Round)
        }
    }
}