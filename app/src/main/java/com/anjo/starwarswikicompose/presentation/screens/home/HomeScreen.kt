package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.presentation.common.SearchBarForChunks
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.common.detail.choosePainter
import com.anjo.starwarswikicompose.presentation.common.loading.ShimmerEffect
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectScreen
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.LARGE_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.CHUNK_LIST
import com.anjo.starwarswikicompose.utils.TestTags.SEARCH_CHUNK_ICON
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(
                color = systemBarColor
        )
    }
    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) }
    ) { padding ->
        HomeContentScreen(padding, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContentScreen(
        paddingValues: PaddingValues,
        navController: NavHostController,
        homeViewModel: HomeScreenViewModel = hiltViewModel(),
) {
    var textState by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()
    var category by remember { mutableStateOf(FILMS) }
    var shouldOpenSearchBar by remember { mutableStateOf(false) }
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }
    val chunksState by homeViewModel.fetchedChunks.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getChunks()
    }

    Scaffold(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Transparent),
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    showAddObjectBottomSheet = true
                }, containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(SMALL_PADDING),
                        shape = RoundedCornerShape(LARGE_PADDING)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
        if (showAddObjectBottomSheet) {
            AddObjectScreen(modifier = Modifier.padding(padding), category = category) { shouldRefresh, localCategory ->
                if (shouldRefresh) {
                    category = localCategory
                    homeViewModel.getChunks(localCategory)
                }
                showAddObjectBottomSheet = false
            }
        }
        Column(modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_BAR_HEIGHT)
                    .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically) {
                CategoryDropDown(modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight(), selectedCategory = category) { cat ->
                    category = cat
                    homeViewModel.getChunks(cat)
                }
                IconButton(modifier = Modifier.weight(1f), onClick = {
                    shouldOpenSearchBar = !shouldOpenSearchBar
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary)
                }
            }
            if (chunksState.isLoading) {
                ShimmerEffect()
            } else {
                if (shouldOpenSearchBar) {
                    SearchBarForChunks(text = textState,
                            onTextChange = { query ->
                                textState = query
                            },
                            onClosedClicked = { shouldOpenSearchBar = false },
                            lazyListState = lazyListState,
                            modifier = Modifier
                                    .clickable {
                                        if (!shouldOpenSearchBar) {
                                            shouldOpenSearchBar = true
                                        }
                                    }
                                    .background(Color.Transparent)
                                    .testTag(SEARCH_CHUNK_ICON),
                            placeholder = ""
                    )
                }
                CommonList(lazyListState, textState,
                        chunksState, navController)
            }
        }
    }
}


@Composable
private fun CommonList(lazyListState: LazyListState,
                       textState: String,
                       chunksState: HomeScreenViewModel.ChunkState,
                       navController: NavHostController) {
    var chunks by remember { mutableStateOf(chunksState.chunks) }

    LazyColumn(modifier = Modifier.testTag(CHUNK_LIST),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
        chunks = if (textState.isBlank()) {
            chunksState.chunks
        } else {
            filterItems(textState, chunksState.chunks)
        }
        items(items = chunks) { item ->
            CommonButton(navController, item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
        modifier: Modifier = Modifier,
        selectedCategory: Category,
        chooseCategory: (Category) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .testTag("Dropdown Menu")
    ) {
        CategoryTextField(selectedCategory, expanded,
                Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable))
        ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(SMALL_BORDER, MaterialTheme.colorScheme.secondary),
                shadowElevation = SMALL_PADDING,
        ) {
            Category.entries.forEach { cat ->
                DropdownMenuItem(onClick = {
                    chooseCategory(cat)
                    expanded = false
                },
                        leadingIcon = {
                            Icon(
                                    painter = choosePainter(cat),
                                    modifier = Modifier.size(HOME_ICON_HEIGHT),
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    contentDescription = null,
                            )
                        },
                        text = {
                            Text(text = cat.categoryName,
                                    modifier = Modifier.padding(SMALL_PADDING),
                                    textAlign = TextAlign.Center,
                                    fontFamily = SOLOFontName,
                                    color = MaterialTheme.colorScheme.onSecondary)
                        }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoryTextField(selectedCategory: Category,
                              expanded: Boolean,
                              modifier: Modifier = Modifier) {
    BasicTextField(
            modifier = modifier,
            value = selectedCategory.name,
            onValueChange = { },
            readOnly = true,
            textStyle = TextStyle(textAlign = TextAlign.Center,
                    fontFamily = SOLOFontName,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary
            ),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                        value = selectedCategory.name,
                        innerTextField = {
                            Box(modifier = Modifier.fillMaxHeight(),
                                    contentAlignment = Alignment.Center) {
                                innerTextField()
                            }
                        },
                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                                top = 0.dp,
                                bottom = 0.dp
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedContainerColor = MaterialTheme.colorScheme.primary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent),
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() })
            }
    )
}

private fun filterItems(searchQuery: String, items: List<UniversalChunkDto>): List<UniversalChunkDto> {
    if (searchQuery.isBlank()) {
        return items
    }
    return items.filter { it.name.lowercase().contains(searchQuery.lowercase().trim()) }
}