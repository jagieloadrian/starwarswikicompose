package com.anjo.starwarswikicompose.presentation.screens.planet.detail

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
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import com.anjo.starwarswikicompose.GetPlanetQuery
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
import com.anjo.starwarswikicompose.presentation.screens.home.formatPopulation
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.PLANETS
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanetContentScreen(
        navController: NavHostController,
        planetViewModel: PlanetViewModel = hiltViewModel(),
) {
    val selectedPlanet by planetViewModel.selectedPlanet.collectAsState()
    selectedPlanet?.let { PlanetVisualisation(it, navController, planetViewModel) }
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
private fun PlanetVisualisation(
        selected: GetPlanetQuery.Planet, navController: NavHostController,
        planetViewModel: PlanetViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val imagesState = remember { mutableStateOf(planetViewModel.images.value) }
    val imagesStateRefresh = remember { mutableStateOf(true) }
    val clipManager = LocalClipboardManager.current
    val snackBarHostState = remember { SnackbarHostState() }
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        isRefreshing = true
        refreshScope.launch {
            planetViewModel.refreshImages(selected.id)
            delay(1500)
            isRefreshing = false
        }
    })

    LaunchedEffect(imagesStateRefresh.value) {
        planetViewModel.refreshImages(selected.id)
        delay(1000)
        imagesState.value = planetViewModel.images.value
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
                AddImageFabWrap(fabExtended, clipManager, navController, selected.name.orEmpty(), {
                    planetViewModel.saveInDatabase(selected, it)
                    imagesStateRefresh.value = true
                }, refreshScope, snackBarHostState)
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isRefreshing) {
                    AsyncImage(model = findImage(selected.id, PLANETS),
                            error = choosePainter(PLANETS),
                            contentDescription = stringResource(R.string.planets),
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
                                stringResource(R.string.diameter_box_name),
                                selected.diameter,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.gravity_box_name),
                                selected.gravity,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.population_box_name),
                                formatPopulation(selected.population),
                                width = thirdWidth)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround) {
                        InfoBox(
                                stringResource(R.string.rotation_period_box_name),
                                selected.rotationPeriod,
                                width = halfWidth)
                        InfoBox(
                                stringResource(R.string.orbital_period_box_name),
                                selected.orbitalPeriod,
                                width = halfWidth)
                    }
                    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                        InfoBoxColumn(
                                stringResource(R.string.climates_box_name),
                                null, selected.climates,
                                width = thirdWidth)
                        InfoBox(
                                stringResource(R.string.surface_water_box_name),
                                selected.surfaceWater,
                                width = thirdWidth)
                        InfoBoxColumn(
                                stringResource(R.string.terrains_box_name),
                                null, selected.terrains,
                                width = thirdWidth)
                    }
                    ShowCharacters(selected, halfWidth, navController)
                    ShowMovies(selected, halfWidth, navController)
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
                                planetViewModel.deleteFromDatabase(it)
                                imagesStateRefresh.value = true
                            })
                }
            }
            PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
private fun ShowCharacters(selectedPlanet: GetPlanetQuery.Planet, halfWidth: Dp, navController: NavHostController) {
    val count = selectedPlanet.residentConnection?.totalCount
    if (shouldInstanceLazyRow(selectedPlanet.residentConnection,
                    count,
                    selectedPlanet.residentConnection?.residents)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedPlanet.residentConnection!!.residents!!) { item ->
                RelatedBox(item!!.id, item.name, PEOPLE, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowMovies(selectedPlanet: GetPlanetQuery.Planet, halfWidth: Dp, navController: NavHostController) {
    val count = selectedPlanet.filmConnection?.totalCount
    if (shouldInstanceLazyRow(selectedPlanet.filmConnection,
                    count,
                    selectedPlanet.filmConnection?.films)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedPlanet.filmConnection!!.films!!) { item ->
                RelatedBox(item!!.id, item.title, Category.FILMS, width = halfWidth, navController = navController)
            }
        }
    }
}