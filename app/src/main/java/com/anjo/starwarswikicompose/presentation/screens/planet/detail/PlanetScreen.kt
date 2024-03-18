package com.anjo.starwarswikicompose.presentation.screens.planet.detail

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
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
import com.anjo.starwarswikicompose.domain.model.Unit
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.presentation.common.DoubleInfoBox
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.common.InfoBox
import com.anjo.starwarswikicompose.presentation.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.common.TripleInfoBox
import com.anjo.starwarswikicompose.presentation.common.choosePainter
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.common.findImage
import com.anjo.starwarswikicompose.services.mapper.formatPopulation
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlanetContentScreen(
        navController: NavHostController,
        planetViewModel: PlanetViewModel = hiltViewModel(),
) {
    val planetState by planetViewModel.selectedPlanet.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        planetViewModel.getPlanet()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshImages = { planetViewModel.refreshImages(planetState.planet.id) },
            selectedName = planetState.planet.name,
            saveInDatabase = { planetViewModel.saveInDatabase(planetState.planet.id, it) },
            navController = navController,
            stateObject = planetState.state,
            content = { padding, state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                PlanetScreenContent(padding, state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, planetState.planet, planetViewModel)
            }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun PlanetScreenContent(
        padding: PaddingValues,
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        selected: Planet,
        planetViewModel: PlanetViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by planetViewModel.images.collectAsState()

    Box(modifier = modifier.fillMaxSize().padding(padding)
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImage(selected.id, PLANETS),
                    error = choosePainter(PLANETS),
                    contentDescription = stringResource(R.string.planets),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(PICTURE_HEIGHT)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(Color.Magenta))
            Text(text = selected.name,
                    fontFamily = SOLOFontName,
                    modifier = Modifier.fillMaxWidth()
                            .height(NAME_PLACEHOLDER_HEIGHT)
                            .basicMarquee(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    color = Color.White
            )
            TripleInfoBox(
                    stringResource(R.string.diameter_box_name),
                    selected.diameter,
                    Unit.KM,
                    stringResource(R.string.gravity_box_name),
                    selected.gravity,
                    null,
                    stringResource(R.string.population_box_name),
                    formatPopulation(selected.population),
                    null,
                    thirdWidth = thirdWidth
            )
            DoubleInfoBox(stringResource(R.string.rotation_period_box_name),
                    selected.rotationPeriod,
                    Unit.H,
                    stringResource(R.string.orbital_period_box_name),
                    selected.orbitalPeriod,
                    Unit.DAYS,
                    halfWidth)
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
                        unit = Unit.PERCENT,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.terrains_box_name),
                        null, selected.terrains,
                        width = thirdWidth)
            }
            ShowHorizontalBoxes(selected.characterConnection, PEOPLE, halfWidth, navController)
            ShowHorizontalBoxes(selected.movieConnection, FILMS, halfWidth, navController)
            GallerySlider(images = imagesState,
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
}