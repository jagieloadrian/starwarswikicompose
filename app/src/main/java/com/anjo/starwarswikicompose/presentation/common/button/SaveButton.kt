package com.anjo.starwarswikicompose.presentation.common.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.TestTags.SAVE_BUTTON_TAG

@Composable
fun SaveButton(text: String, isSaving: Boolean, enabled: () -> Boolean,
               onClick: () -> Unit) {
    TextButton(onClick = { onClick() },
            enabled = enabled(),
            shape = RoundedCornerShape(SMALL_PADDING),
            colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)),
        modifier = Modifier.testTag(SAVE_BUTTON_TAG)) {
        if (isSaving) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onSecondary,
                    strokeWidth = SMALL_BORDER)
        } else {
            Text(
                    text,
                    color = if (enabled()) MaterialTheme.colorScheme.onSecondary
                    else MaterialTheme.colorScheme.onSecondary.copy(0.5f),
                    fontFamily = SOLOFontName,
                    style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}