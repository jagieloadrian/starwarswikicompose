package com.anjo.starwarswikicompose.presentation.screens.common

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_WIDTH
import com.anjo.starwarswikicompose.ui.theme.topAppBarContentColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarHomeBackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddImageFab(
        modifier: Modifier = Modifier,
        extended: Boolean,
        onClick: () -> Unit,
) {
    FloatingActionButton(
            modifier = modifier,
            onClick = onClick,
            backgroundColor = MaterialTheme.colors.topAppBarHomeBackgroundColor,
            shape = RoundedCornerShape(MEDIUM_PADDING)
    ) {
        Row(modifier = Modifier.padding(MEDIUM_PADDING),
                verticalAlignment = Alignment.CenterVertically) {
            Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.topAppBarContentColor
            )
            AnimatedVisibility(visible = extended) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.padding(PAGING_INDICATOR_WIDTH))
                    Text(text = "Add Image",
                            color = MaterialTheme.colors.topAppBarContentColor)
                }
            }
        }
    }
}