package com.anjo.starwarswikicompose.presentation.screens.specie.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.GetSpecieQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.AddImageFab
import com.anjo.starwarswikicompose.presentation.screens.common.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.screens.common.RelatedBox
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.clickableArrangementInLazyRow
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.presentation.screens.common.shouldInstanceLazyRow
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.addImageFunction
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpecieContentScreen(
        navController: NavHostController,
        specieViewModel: SpecieViewModel = hiltViewModel(),
) {
    val selectedSpecie by specieViewModel.selectedSpecie.collectAsState()
    selectedSpecie?.let { SpecieVisualisation(it, navController, specieViewModel) }
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
private fun SpecieVisualisation(
        selected: GetSpecieQuery.Species, navController: NavHostController,
        specieViewModel: SpecieViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val imagesState = specieViewModel.images.toMutableList()
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val clipManager = LocalClipboardManager.current

    LaunchedEffect(imagesStateRefresh.value) {
        specieViewModel.refreshImages(selected.id)
        delay(1000)
        imagesStateRefresh.value = false
    }

    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            specieViewModel.refreshImages(selected.id)
            delay(1500)
            isRefreshing = false
        }
    })


    LaunchedEffect(state) {
        var prev = 0
        snapshotFlow { state.value }.collect {
            fabExtended = it <= prev
            prev = it
        }
    }

    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) },
            floatingActionButton = {
                AddImageFab(extended = fabExtended) {
                    addImageFunction(clipManager, navController) {
                        specieViewModel.saveInDatabase(selected, it)
                        imagesStateRefresh.value = true
                    }
                }
            }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isRefreshing) {
                    AsyncImage(model = findImage(selected.id, SPECIES),
                            error = choosePainter(SPECIES),
                            contentDescription = stringResource(R.string.species),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                    .height(PICTURE_HEIGHT)
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .clip(CircleShape)
                                    .background(Color.Magenta))
                    Text(text = selected.name.orEmpty(),
                            fontFamily = SOLOFontName,
                            modifier = Modifier.fillMaxWidth()
                                    .height(NAME_PLACEHOLDER_HEIGHT)
                                    .basicMarquee(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2,
                            color = Color.White
                    )
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBox(
                                stringResource(R.string.language_box_name),
                                selected.language,
                                width = halfWidth)
                        InfoBox(
                                stringResource(R.string.homeworld_box_name),
                                selected.homeworld?.name,
                                id = selected.homeworld?.id,
                                category = Category.PLANETS,
                                width = halfWidth,
                                navController)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBox(
                                stringResource(R.string.classification_box_name),
                                selected.classification,
                                width = halfWidth)
                        InfoBox(
                                stringResource(R.string.designation_box_name),
                                selected.designation,
                                width = halfWidth)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBox(
                                stringResource(R.string.avr_height_box_name),
                                selected.averageHeight,
                                width = halfWidth)
                        InfoBox(
                                stringResource(R.string.avr_lifespan_box_name),
                                selected.averageLifespan,
                                width = halfWidth)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBoxColumn(
                                stringResource(R.string.eye_colors_box_name),
                                null, selected.eyeColors,
                                width = thirdWidth)
                        InfoBoxColumn(
                                stringResource(R.string.hair_colors_box_name),
                                null, selected.hairColors,
                                width = thirdWidth)
                        InfoBoxColumn(
                                stringResource(R.string.skin_colors_box_name),
                                null, selected.skinColors,
                                width = thirdWidth)
                    }
                    ShowCharacters(selected, halfWidth, navController)
                    ShowMovies(selected, halfWidth, navController)
                    GallerySlider(images = imagesState,
                            onCLickLeft = {
                                specieViewModel.deleteFromDatabase(it)
                                imagesStateRefresh.value = true
                            },
                            onCLickRight = {
                                specieViewModel.refreshImages(selected.id)
                            })
                }
            }
            PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
private fun ShowCharacters(selectedSpecie: GetSpecieQuery.Species, halfWidth: Dp, navController: NavHostController) {
    val count = selectedSpecie.personConnection?.totalCount
    if (shouldInstanceLazyRow(selectedSpecie.personConnection,
                    count,
                    selectedSpecie.personConnection?.people)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedSpecie.personConnection!!.people!!) { item ->
                RelatedBox(item!!.id, item.name, PEOPLE, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowMovies(selectedSpecie: GetSpecieQuery.Species, halfWidth: Dp, navController: NavHostController) {
    val count = selectedSpecie.filmConnection?.totalCount
    if (shouldInstanceLazyRow(selectedSpecie.filmConnection,
                    count,
                    selectedSpecie.filmConnection?.films)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedSpecie.filmConnection!!.films!!) { item ->
                RelatedBox(item!!.id, item.title, Category.FILMS, width = halfWidth, navController = navController)
            }
        }
    }
}