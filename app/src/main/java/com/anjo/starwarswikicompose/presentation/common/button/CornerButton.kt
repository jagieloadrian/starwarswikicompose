package com.anjo.starwarswikicompose.presentation.common.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.anjo.starwarswikicompose.presentation.common.animatedBorder
import com.anjo.starwarswikicompose.ui.theme.SHIMMER_COLORS
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.utils.Constants.ANIMATED_BORDER_DURATION
import com.anjo.starwarswikicompose.utils.TestTags.CORNER_BUTTON_TAG

@Composable
fun CornerButton(
        imageVector: ImageVector,
        contentDescription: String,
        onClick: () -> Unit,
) {
    CornerButtonFrame(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = contentDescription,
                modifier = Modifier.background(Color.Transparent),
                tint = Color.White)
    }
}

@Composable
fun CornerButton(
        painter: Painter,
        onClick: () -> Unit,
) {
    CornerButtonFrame(onClick = onClick) {
        Icon(painter = painter, contentDescription = "",
                modifier = Modifier
                        .background(Color.Transparent),
                tint = Color.White)
    }
}


@Composable
fun CornerButtonFrame(
        onClick: () -> Unit,
        icon: @Composable () -> Unit
) {
    IconButton(onClick = onClick, modifier = Modifier
            .background(Color.Transparent)
            .padding(15.dp)
        .testTag(CORNER_BUTTON_TAG)) {
        Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                        .background(Color.DarkGray, shape = CircleShape)
                        .animatedBorder(borderColors = SHIMMER_COLORS,
                                backgroundColor = Color.DarkGray,
                                animationDurationInMillis = ANIMATED_BORDER_DURATION,
                                borderWidth = SMALL_BORDER)
                        .alpha(0.5f)
                        .padding(10.dp)) {
            icon()
        }
    }
}