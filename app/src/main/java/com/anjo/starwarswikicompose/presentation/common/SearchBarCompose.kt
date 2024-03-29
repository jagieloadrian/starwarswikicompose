package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING_FOR_INFOBOX
import com.anjo.starwarswikicompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.utils.Constants.SEARCH_BAR_LABEL

@Composable
fun SearchBar(
        modifier: Modifier = Modifier,
        text: String,
        onTextChange: (String) -> Unit,
        onSearchClicked: (String) -> Unit,
        onClosedClicked: () -> Unit,
        enabled: Boolean,
        lazyListState: LazyListState,
        placeholder: String,
) {
    val focusManager = LocalFocusManager.current
    Surface(modifier = modifier) {
        TextField(
                modifier = modifier
                        .fillMaxWidth()
                        .animateContentSize(animationSpec = tween(durationMillis = 900))
                        .height(if (lazyListState.isScrollInProgress) 0.dp else TOP_APP_BAR_HEIGHT),
                value = text,
                enabled = enabled,
                onValueChange = { onTextChange(it) },
                leadingIcon = {
                    IconButton(
                            modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                            onClick = {
                                onSearchClicked(text)
                                focusManager.clearFocus()
                            }
                    ) {
                        Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_icon),
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                            modifier = Modifier.semantics {
                                contentDescription = "CloseButton"
                            },
                            onClick = {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onClosedClicked()
                                }
                            }
                    ) {
                        Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close_icon)
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                ),
                placeholder = {
                    Text(placeholder)
                },
                keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClicked(text)
                            focusManager.clearFocus()
                        }
                ),
                keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                )
        )
    }
}

@Composable
fun SearchBarForChunks(
        modifier: Modifier = Modifier,
        text: String,
        onTextChange: (String) -> Unit,
        onClosedClicked: () -> Unit,
        enabled: Boolean,
        lazyListState: LazyListState,
        placeholder: String,
) {
    Surface(modifier = modifier
            .testTag(SEARCH_BAR_LABEL),
            color = Color.Transparent,
            shape = RoundedCornerShape(SMALL_PADDING_FOR_INFOBOX),
            border = BorderStroke(SMALL_BORDER, MaterialTheme.colors.mainBackgroundColors)) {
        TextField(
                modifier = modifier
                        .fillMaxWidth()
                        .animateContentSize(animationSpec = tween(durationMillis = 800))
                        .height(if (lazyListState.isScrollInProgress) 0.dp else TOP_APP_BAR_HEIGHT),
                value = text,
                enabled = enabled,
                onValueChange = { onTextChange(it) },
                leadingIcon = {
                    Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            tint = MaterialTheme.colors.mainBackgroundColors
                    )
                },
                trailingIcon = {
                    IconButton(
                            modifier = Modifier.semantics {
                                contentDescription = "CloseButton"
                            },
                            onClick = {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onClosedClicked()
                                }
                            }
                    ) {
                        Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close_icon),
                                tint = MaterialTheme.colors.mainBackgroundColors
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = MaterialTheme.colors.mainBackgroundColors

                ),
                placeholder = {
                    Text(placeholder)
                }
        )
    }
}