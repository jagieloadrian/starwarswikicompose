package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
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
import com.anjo.starwarswikicompose.GetFilmQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.AddImageFabWrap
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.screens.common.RelatedBox
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.appbars.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.clickableArrangementInLazyRow
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.presentation.screens.common.shouldInstanceLazyRow
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category.FILMS
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.PLANETS
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.Category.VEHICLES
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MovieContentScreen(
        navController: NavHostController,
        movieViewModel: MovieViewModel = hiltViewModel(),
) {
    val selectedMovie by movieViewModel.selectedMovie.collectAsState()
    selectedMovie?.let { MovieVisualisation(it, navController, movieViewModel) }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun MovieVisualisation(
        selected: GetFilmQuery.Film,
        navController: NavHostController,
        movieViewModel: MovieViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val twoThirdsWidth = thirdWidth * 2
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val imagesState = remember { mutableStateOf(movieViewModel.images.value) }
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val clipManager = LocalClipboardManager.current
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            movieViewModel.refreshImages(selected.id)
            delay(1500)
            isRefreshing = false
        }
    })

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(imagesStateRefresh.value) {
        movieViewModel.refreshImages(selected.id)
        delay(1000)
        imagesState.value = movieViewModel.images.value
        imagesStateRefresh.value = false
    }


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
                AddImageFabWrap(fabExtended, clipManager, navController, selected.title.orEmpty(), {
                    movieViewModel.saveInDatabase(selected, it)
                    imagesStateRefresh.value = true
                }, refreshScope, snackBarHostState)
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
                .pullRefresh(pullRefreshState)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isRefreshing) {
                    AsyncImage(model = findImage(selected.id, FILMS),
                            error = choosePainter(FILMS),
                            contentDescription = stringResource(R.string.movies),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                    .height(PICTURE_HEIGHT)
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                                    .background(Color.Magenta))
                    Text(text = selected.title.orEmpty(),
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
                            horizontalArrangement = Arrangement.SpaceAround) {
                        InfoBox(
                                stringResource(R.string.episode_id_box_name),
                                selected.episodeID,
                                width = thirdWidth)
                        InfoBoxDialog(
                                stringResource(R.string.opening_crawl_box_name),
                                selected.openingCrawl,
                                width = twoThirdsWidth)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBoxColumn(
                                stringResource(R.string.producers_box_name),
                                null,
                                selected.producers,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.director_box_name),
                                selected.director,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.release_date_box_name),
                                selected.releaseDate,
                                width = thirdWidth)
                    }
                    ShowCharacters(selected, halfWidth, navController)
                    ShowPlanets(selected, halfWidth, navController)
                    ShowStarships(selected, halfWidth, navController)
                    ShowVehicles(selected, halfWidth, navController)
                    ShowSpecies(selected, halfWidth, navController)
                    GallerySlider(images = imagesState.value,
                            onCLickLeft = {
                                refreshScope.launch {
                                    snackBarHostState.showSnackbar(Constants.REFRESH_IMAGES)
                                }
                                imagesStateRefresh.value = true
                            },
                            onCLickRight = {
                                refreshScope.launch {
                                    snackBarHostState.showSnackbar(Constants.DELETE_AND_REFRESH_IMAGES)
                                }
                                movieViewModel.deleteFromDatabase(it)
                                imagesStateRefresh.value = true
                            })
                }
            }
            PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}


@Composable
private fun ShowCharacters(selectedMovie: GetFilmQuery.Film, halfWidth: Dp, navController: NavHostController) {
    val count = selectedMovie.characterConnection?.totalCount
    if (shouldInstanceLazyRow(selectedMovie.characterConnection,
                    count,
                    selectedMovie.characterConnection?.characters)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedMovie.characterConnection!!.characters!!) { item ->
                RelatedBox(item!!.id, item.name, PEOPLE, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowPlanets(selectedMovie: GetFilmQuery.Film, halfWidth: Dp, navController: NavHostController) {
    val count = selectedMovie.planetConnection?.totalCount
    if (shouldInstanceLazyRow(selectedMovie.planetConnection,
                    selectedMovie.planetConnection?.totalCount,
                    selectedMovie.planetConnection?.planets)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedMovie.planetConnection!!.planets!!) { item ->
                RelatedBox(item!!.id, item.name, PLANETS, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowVehicles(selectedMovie: GetFilmQuery.Film, halfWidth: Dp, navController: NavHostController) {
    val count = selectedMovie.vehicleConnection?.totalCount
    if (shouldInstanceLazyRow(selectedMovie.vehicleConnection,
                    count,
                    selectedMovie.vehicleConnection?.vehicles)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedMovie.vehicleConnection!!.vehicles!!) { item ->
                RelatedBox(item!!.id, item.name, VEHICLES, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowStarships(selectedMovie: GetFilmQuery.Film, halfWidth: Dp, navController: NavHostController) {
    val count = selectedMovie.starshipConnection?.totalCount
    if (shouldInstanceLazyRow(selectedMovie.starshipConnection,
                    count,
                    selectedMovie.starshipConnection?.starships)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedMovie.starshipConnection!!.starships!!) { item ->
                RelatedBox(item!!.id, item.name, STARSHIPS, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowSpecies(selectedMovie: GetFilmQuery.Film, halfWidth: Dp, navController: NavHostController) {
    val count = selectedMovie.speciesConnection?.totalCount
    if (shouldInstanceLazyRow(selectedMovie.speciesConnection,
                    count,
                    selectedMovie.speciesConnection?.species)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedMovie.speciesConnection!!.species!!) { item ->
                RelatedBox(item!!.id, item.name, SPECIES, width = halfWidth, navController = navController)
            }
        }
    }
}