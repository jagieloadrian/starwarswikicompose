package com.anjo.starwarswikicompose.presentation.screens.movie.detail

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.anjo.GetFilmQuery
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
import com.anjo.starwarswikicompose.utils.getLocalWidth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieContentScreen(
        navController: NavHostController,
        movieViewModel: MovieViewModel = hiltViewModel(),
) {
    val selectedMovie by movieViewModel.selectedMovie.collectAsState()
    selectedMovie?.let { MovieVisualisation(it, navController, movieViewModel) }
}

@ExperimentalFoundationApi
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
    val images = movieViewModel.images.collectAsState()
    val clipManager = LocalClipboardManager.current

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
                    val photoUrl = clipManager.getText()?.text
                    photoUrl?.let { movieViewModel.saveInDatabase(selected, it) }
                }
            }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                if (images.value.isNotEmpty()) {
                    GallerySlider(images = images.value)
                }
            }
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