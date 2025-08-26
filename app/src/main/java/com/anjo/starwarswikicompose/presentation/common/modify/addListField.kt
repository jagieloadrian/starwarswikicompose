package com.anjo.starwarswikicompose.presentation.common.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING


@Composable
fun addListField(modifier: Modifier = Modifier, focusManager: FocusManager,
                 placeholder: String, label: String, firstValue: List<String> = listOf()): SnapshotStateList<String> {
    var newObject by remember { mutableStateOf("") }
    val results = remember { mutableStateListOf(*firstValue.toTypedArray()) }

    Column(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = SMALL_PADDING)
                    .testTag("ListField$label")
    ) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                    value = newObject,
                    onValueChange = { newObject = it },
                    leadingIcon = {
                        Icon(Icons.Filled.Add, contentDescription = "AddNewObjectField",
                                tint = MaterialTheme.colorScheme.onSecondary)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    placeholder = { Text(placeholder) },
                    label = { Text(label) },
                    singleLine = true,
                    maxLines = 1,
                    visualTransformation = VisualTransformation.None,
                    modifier = modifier
                            .weight(1f)
                            .background(Brush.linearGradient(
                                    listOf(
                                            MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary,
                                    )),
                                    shape = RoundedCornerShape(SMALL_PADDING), alpha = 0.5f),
                    shape = RoundedCornerShape(SMALL_PADDING),
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
            Spacer(modifier = modifier.width(8.dp))
            Button(
                    onClick = {
                        if (newObject.isNotBlank()) {
                            results.add(newObject)
                            newObject = ""
                        }
                    },
                    enabled = newObject.isNotBlank()
            ) {
                Text("Add")
            }
        }
        LazyColumn(
                modifier = modifier
                        .heightIn(max = 150.dp)
                        .fillMaxWidth()
        ) {
            items(results) { name ->
                Row(
                        modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(Brush.linearGradient(
                                        listOf(
                                                MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary,
                                        )),
                                        shape = RoundedCornerShape(SMALL_PADDING), alpha = 0.5f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                            name, textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = { results.remove(name) }) {
                        Icon(Icons.Default.Close, contentDescription = "Remove")
                    }
                }
            }
        }
    }
    return results
}