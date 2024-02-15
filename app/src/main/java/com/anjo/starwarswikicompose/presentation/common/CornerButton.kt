package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CornerButton(
        imageVector: ImageVector,
        contentDescription: String,
        onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = Modifier
            .background(Color.Transparent)
            .padding(15.dp)) {
        Box(contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.DarkGray, shape = CircleShape)
                        .alpha(0.5f)
                        .padding(15.dp)) {
            Icon(imageVector = imageVector, contentDescription = contentDescription,
                    modifier = Modifier.background(Color.Transparent),
                    tint = Color.White)
        }
    }
}

@Composable
fun CornerButton(
        painter: Painter,
        onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = Modifier
            .background(Color.Transparent)
            .padding(15.dp)) {
        Box(contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.DarkGray, shape = CircleShape)
                        .alpha(0.5f)
                        .padding(15.dp)) {
            Icon(painter = painter, contentDescription = "",
                    modifier = Modifier.background(Color.Transparent),
                    tint = Color.White)
        }
    }
}