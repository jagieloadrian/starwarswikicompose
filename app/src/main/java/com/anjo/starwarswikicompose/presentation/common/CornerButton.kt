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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CornerButton(
        imageVector: ImageVector,
        onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = Modifier
            .background(Color.Transparent)
            .padding(15.dp)) {
        Box(contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.DarkGray, shape = CircleShape)
                        .padding(15.dp)
                        .alpha(0.5f)) {
            Icon(imageVector = imageVector, contentDescription = "",
                    modifier = Modifier.background(Color.Transparent),
                    tint = Color.White)
        }
    }
}