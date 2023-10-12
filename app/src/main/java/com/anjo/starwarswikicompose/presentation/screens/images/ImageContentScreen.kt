package com.anjo.starwarswikicompose.presentation.screens.images

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R

@Composable
fun ImageScreen(
        navHostController: NavHostController,
        imageViewModel: ImageViewModel = hiltViewModel()
) {

    val searchQuery by imageViewModel.searchQuery
    val photoResponse by imageViewModel.fetchedPhotoInfos.collectAsState()
    val recentPhotos by imageViewModel.fetchedRecentPhotos.collectAsState()
    val enabled = remember { mutableStateOf(true) }

    Log.e("ImageScreen", photoResponse.toString())

    Column(modifier = Modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)
    ) {
        SearchBar(text = searchQuery,
                onTextChange = { imageViewModel.updateSearchQuery(query = it) },
                onSearchClicked = {},
                onClosedClicked = {
                    enabled.value = !enabled.value
                },
                enabled = enabled.value)
        Text(text = recentPhotos.toString(), color = Color.White)
    }
}


@Composable
fun SearchBar(modifier: Modifier = Modifier,
              text: String,
              onTextChange: (String) -> Unit,
              onSearchClicked: (String) -> Unit,
              onClosedClicked: () -> Unit,
              enabled:Boolean
) {
    Surface(modifier = modifier) {
        TextField(
                value = text,
                enabled = enabled,
                onValueChange = { onTextChange(it) },
                leadingIcon = {
                    IconButton(
                            modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                            onClick = { onSearchClicked(text) }
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
                    Text(stringResource(R.string.placeholder_search))
                },
                modifier = modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp),
                keyboardActions = KeyboardActions(
                        onSearch = { onSearchClicked(text) }
                ),
                keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                )
        )
    }
}