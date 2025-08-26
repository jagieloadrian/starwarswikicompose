package com.anjo.starwarswikicompose.presentation.common.modify

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.toMutableStateList
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
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.TOP_APP_BAR_HEIGHT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addChunksField(modifier: Modifier = Modifier, focusManager: FocusManager, placeholder: String,
                   label: String, potentialChunks: List<UniversalChunkDto>,
                   firstValue: List<UniversalChunkDto> = listOf()): ConnectionDto {
    var searchText by remember { mutableStateOf("") }
    var modifiedPotentialChunks = remember(potentialChunks) { mutableStateListOf(*potentialChunks.toTypedArray()) }
    var newObject by remember { mutableStateOf(UniversalChunkDto(category = Category.ALL)) }
    val results = remember { mutableStateListOf(*firstValue.toTypedArray()) }
    var expanded by remember { mutableStateOf(false) }

    Column(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = SMALL_PADDING)
                    .testTag("ChunksField$label")
    ) {
        ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = modifier
                        .fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            modifiedPotentialChunks = modifiedPotentialChunks
                                    .filter { chunk -> chunk.name.contains(searchText, ignoreCase = true) }
                                    .toMutableStateList()
                        },
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
                                        shape = RoundedCornerShape(SMALL_PADDING), alpha = 0.5f)
                                .clickable { expanded = true }
                                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable),
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
                if (potentialChunks.isNotEmpty()) {
                    Button(
                            onClick = {
                                if (newObject.category != Category.ALL) {
                                    if (!results.contains(newObject)) {
                                        modifiedPotentialChunks.remove(newObject)
                                        results.add(newObject)
                                    }
                                    newObject = UniversalChunkDto(category = Category.ALL)
                                    searchText = ""
                                }
                            },
                            enabled = newObject.category != Category.ALL
                    ) {
                        Text("Add")
                    }
                } else {
                    CircularProgressIndicator(
                            modifier = modifier.align(Alignment.CenterVertically),
                            color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier,
                    shape = RoundedCornerShape(SMALL_PADDING),
                    containerColor = MaterialTheme.colorScheme.primary,
                    border = BorderStroke(SMALL_BORDER, MaterialTheme.colorScheme.secondary)) {
                modifiedPotentialChunks.forEach { chunk ->
                    DropdownMenuItem(
                            text = { Text(chunk.name) },
                            onClick = {
                                searchText = chunk.name
                                newObject = chunk
                                expanded = false
                            },
                    )
                }
            }
        }
        LazyHorizontalGrid(
                modifier = modifier.height(TOP_APP_BAR_HEIGHT),
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalArrangement = Arrangement.Center
        ) {
            items(results) { name ->
                Row(
                        modifier = modifier
                                .padding(SMALL_PADDING)
                                .background(Brush.linearGradient(
                                        listOf(
                                                MaterialTheme.colorScheme.secondary,
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary,
                                        )),
                                        shape = RoundedCornerShape(MEDIUM_PADDING), alpha = 0.5f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                            text = name.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier,
                    )
                    IconButton(onClick = {
                        results.remove(name)
                        modifiedPotentialChunks.add(name)
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Remove")
                    }
                }
            }
        }
    }
    return ConnectionDto(results.distinct().size, results.distinct().toList())
}