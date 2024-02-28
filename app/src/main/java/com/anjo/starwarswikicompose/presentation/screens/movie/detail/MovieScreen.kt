package com.anjo.starwarswikicompose.presentation.screens.movie.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.screens.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants.DELETE_AND_REFRESH_IMAGES
import com.anjo.starwarswikicompose.utils.Constants.REFRESH_IMAGES
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MovieContentScreen(
        navController: NavHostController,
        movieViewModel: MovieViewModel = hiltViewModel(),
) {
    val movieState by movieViewModel.selectedMovie.collectAsState()
    val init = remember { mutableStateOf(true) }
    if (init.value) {
        movieViewModel.getMovie()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshImages = { movieViewModel.refreshImages(movieState.movie.id) },
            selectedName = movieState.movie.title,
            saveInDatabase = { movieViewModel.saveInDatabase(movieState.movie.id, it) },
            navController = navController,
            stateObject = movieState.state,
            content = { padding, state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                MovieScreen(padding, state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, movieState.movie, movieViewModel)
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieScreen(
        padding: PaddingValues,
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        selected: Movie,
        movieViewModel: MovieViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val twoThirdsWidth = thirdWidth * 2
    val imagesState by movieViewModel.images.collectAsState()

    Box(modifier = modifier.fillMaxSize().padding(padding)
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = findImage(selected.id, FILMS),
                    error = choosePainter(FILMS),
                    contentDescription = stringResource(R.string.movies),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(PICTURE_HEIGHT)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                            .background(Color.Magenta))
            Text(text = selected.title,
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
                        selected.episodeId,
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
            ShowHorizontalBoxes(selected.characterConnection, PEOPLE, halfWidth, navController)
            ShowHorizontalBoxes(selected.planetConnection, PLANETS, halfWidth, navController)
            ShowHorizontalBoxes(selected.starshipConnection, STARSHIPS, halfWidth, navController)
            ShowHorizontalBoxes(selected.vehicleConnection, VEHICLES, halfWidth, navController)
            ShowHorizontalBoxes(selected.specieConnection, SPECIES, halfWidth, navController)
            GallerySlider(images = imagesState,
                    onCLickLeft = {
                        refreshScope.launch {
                            snackBarHostState.showSnackbar(REFRESH_IMAGES)
                        }
                        imagesStateRefresh.value = true
                    },
                    onCLickRight = {
                        refreshScope.launch {
                            snackBarHostState.showSnackbar(DELETE_AND_REFRESH_IMAGES)
                        }
                        movieViewModel.deleteFromDatabase(it)
                        imagesStateRefresh.value = true
                    })
        }
    }
}