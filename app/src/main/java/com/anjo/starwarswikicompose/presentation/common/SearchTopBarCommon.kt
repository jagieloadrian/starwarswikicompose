package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.topAppBarBackgroundColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarContentColor

@Composable
fun SearchTopBar(
        text: String,
        onTextChange: (String) -> Unit,
        onSearchClicked: (String) -> Unit,
        onClosedClicked: () -> Unit
) {
    SearchWidget(
            text = text,
            onTextChange = onTextChange,
            onSearchClicked = onSearchClicked,
            onClosedClicked = onClosedClicked
    )
}

@Composable
fun SearchWidget(
        text: String,
        onTextChange: (String) -> Unit,
        onSearchClicked: (String) -> Unit,
        onClosedClicked: () -> Unit
) {
    Surface(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_APP_BAR_HEIGHT)
                    .semantics {
                        contentDescription = "SearchWidget"
                    },
            elevation = AppBarDefaults.TopAppBarElevation,
            color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
                modifier = Modifier.fillMaxWidth()
                        .semantics {
                            contentDescription = "TextField"
                        },
                value = text,
                onValueChange = { onTextChange(it) },
                placeholder = {
                    Text(
                            modifier = Modifier.alpha(ContentAlpha.medium),
                            text = "Search here...",
                            color = Color.White
                    )
                },
                textStyle = TextStyle(
                        color = MaterialTheme.colors.topAppBarContentColor
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                            modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                            onClick = {}
                    ) {
                        Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_icon),
                                tint = MaterialTheme.colors.topAppBarContentColor
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
                                contentDescription = stringResource(R.string.close_icon),
                                tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClicked(text)
                        }
                ),
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = MaterialTheme.colors.topAppBarContentColor
                )
        )
    }
}
