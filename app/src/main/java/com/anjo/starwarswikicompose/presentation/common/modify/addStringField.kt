package com.anjo.starwarswikicompose.presentation.common.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING

@Composable
fun addStringField(modifier: Modifier = Modifier,
                   focusManager: FocusManager,
                   singleLine: Boolean = true,
                   maxLines: Int = 1,
                   placeholder: String,
                   label: String,
                   firstValue: String = "",
                   validation: (String) -> Boolean): String {
    var string by remember { mutableStateOf(firstValue) }
    OutlinedTextField(
            value = string,
            onValueChange = { string = it },
            leadingIcon = {
                Icon(if (validation(string.trim())) Icons.Filled.Check else Icons.Filled.Add,
                        contentDescription = "AddStringField",
                        tint = MaterialTheme.colorScheme.onSecondary)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            placeholder = { Text(placeholder) },
            label = { Text(label) },
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = VisualTransformation.None,
            modifier = modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(
                            listOf(
                                    MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary,
                            )),
                            shape = RoundedCornerShape(SMALL_PADDING), alpha = 0.5f)
                    .testTag("StringField$label"),
            shape = RoundedCornerShape(SMALL_PADDING),
            isError = validation(string.trim()),
            colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.onSecondary,
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            )
    )
    return string
}