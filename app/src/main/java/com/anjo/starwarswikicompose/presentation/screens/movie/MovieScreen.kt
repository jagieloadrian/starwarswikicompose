package com.anjo.starwarswikicompose.presentation.screens.movie

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.ConnectionTitle
import com.anjo.starwarswikicompose.presentation.common.GallerySliderPart
import com.anjo.starwarswikicompose.presentation.common.create.models.AddMovieObject
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBox
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.common.detail.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.common.detail.choosePainter
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalWidth
import com.anjo.starwarswikicompose.presentation.common.update.CopyModelBottomModal
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.services.imagefetcher.findImageAsset
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.PLANETS_NAME
import com.anjo.starwarswikicompose.utils.Constants.SPECIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.MOVIE_SCREEN_TAG
import com.anjo.starwarswikicompose.utils.updateImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MovieContentScreen(
        navController: NavHostController,
        movieViewModel: MovieViewModel = hiltViewModel(),
        addViewModel: AddObjectViewModel = hiltViewModel()) {
    val movieState by movieViewModel.selectedMovie.collectAsState()
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }
    var init by remember { mutableStateOf(true) }
    if (init) {
        movieViewModel.getMovie()
        init = false
    }

    DetailVisualisationComponent(
            refreshObject = { movieViewModel.getMovie(); movieViewModel.refreshImages(movieState.movieDto.id) },
            saveImageInDatabase = {
                movieViewModel.saveInDatabase(movieState.movieDto.id, it); movieViewModel.getMovie()
            },
            updateObjectFab = { showAddObjectBottomSheet = !showAddObjectBottomSheet },
            navController = navController,
            stateObject = movieState.state,
            removeObjectHandler = Pair(movieState.movieDto.isFromLocalStore) {
                movieViewModel.removeMovie(movieState.movieDto.id)
                navController.navigate(Screen.Home.route)
            },
            content = { state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                if (showAddObjectBottomSheet) {
                    CopyModelBottomModal(modifier, onDismiss = { showAddObjectBottomSheet = false }) {
                        AddMovieObject(modifier, movieState.movieDto
                                .copy(title = "${movieState.movieDto.title}-Copy"), addViewModel) { newId ->
                            scope.launch {
                                updateImages(movieViewModel.images) {
                                    movieViewModel.saveInDatabase(newId, it)
                                }
                                snackBarHostState.showSnackbar("Updated Movie ${movieState.movieDto.title}")
                            }
                            showAddObjectBottomSheet = false
                        }
                    }
                }
                MovieScreen(state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, movieViewModel)
            }
    )
}

@Composable
fun MovieScreen(
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        movieViewModel: MovieViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val twoThirdsWidth = thirdWidth * 2
    val imagesState by movieViewModel.images.collectAsState()
    val movieState by movieViewModel.selectedMovie.collectAsState()
    val selected by remember { mutableStateOf(movieState.movieDto) }

    Box(modifier = modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)
            .testTag(MOVIE_SCREEN_TAG)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = findImageAsset(selected.id, FILMS, selected.isFromLocalStore,
                    LocalContext.current),
                    error = choosePainter(FILMS),
                    contentDescription = stringResource(R.string.movies),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(PICTURE_HEIGHT)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                            .background(Color.Transparent))
            Text(text = selected.title,
                    fontFamily = SOLOFontName,
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(NAME_PLACEHOLDER_HEIGHT)
                            .basicMarquee(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
            )
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                InfoBox(
                        cornerName = stringResource(R.string.episode_id_box_name),
                        name = selected.episodeId,
                        width = thirdWidth)
                InfoBoxDialog(
                        cornerName = stringResource(R.string.opening_crawl_box_name),
                        description = selected.openingCrawl,
                        width = twoThirdsWidth)
            }
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.producers_box_name),
                        null,
                        selected.producers,
                        width = thirdWidth)
                InfoBox(
                        cornerName = stringResource(R.string.director_box_name),
                        name = selected.director,
                        width = thirdWidth)
                InfoBox(
                        cornerName = stringResource(R.string.release_date_box_name),
                        name = selected.releaseDate,
                        width = thirdWidth)
            }
            ConnectionTitle(text = HEROES_NAME, shouldShowTitle = selected.characterConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.characterConnection, halfWidth, navController)
            ConnectionTitle(text = PLANETS_NAME, shouldShowTitle = selected.planetConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.planetConnection, halfWidth, navController)
            ConnectionTitle(text = STARSHIPS_NAME, shouldShowTitle = selected.starshipConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.starshipConnection, halfWidth, navController)
            ConnectionTitle(text = VEHICLES_NAME, shouldShowTitle = selected.vehicleConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.vehicleConnection, halfWidth, navController)
            ConnectionTitle(text = SPECIES_NAME, shouldShowTitle = selected.specieConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.specieConnection, halfWidth, navController)
            GallerySliderPart(imagesState, refreshScope, snackBarHostState, imagesStateRefresh) { imageSliderModel ->
                movieViewModel.deleteFromDatabase(imageSliderModel)
            }
        }
    }
}