package com.anjo.starwarswikicompose.presentation.screens.planet

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.mapper.formatPopulation
import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.ConnectionTitle
import com.anjo.starwarswikicompose.presentation.common.GallerySliderPart
import com.anjo.starwarswikicompose.presentation.common.create.models.AddPlanetObject
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.common.detail.DoubleInfoBox
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBox
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.common.detail.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.common.detail.TripleInfoBox
import com.anjo.starwarswikicompose.presentation.common.detail.choosePainter
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalWidth
import com.anjo.starwarswikicompose.presentation.common.update.CopyModelBottomModal
import com.anjo.starwarswikicompose.presentation.screens.create.AddObjectViewModel
import com.anjo.starwarswikicompose.services.imagefetcher.findImageAsset
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.updateImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlanetContentScreen(
        navController: NavHostController,
        planetViewModel: PlanetViewModel = hiltViewModel(),
        addViewModel: AddObjectViewModel = hiltViewModel()) {
    val planetState by planetViewModel.selectedPlanet.collectAsState()
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        planetViewModel.getPlanet()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshObject = { planetViewModel.getPlanet(); planetViewModel.refreshImages(planetState.planetDto.id) },
            saveImageInDatabase = {
                planetViewModel.saveInDatabase(planetState.planetDto.id, it); planetViewModel.getPlanet()
            },
            updateObjectFab = { showAddObjectBottomSheet = !showAddObjectBottomSheet },
            navController = navController,
            stateObject = planetState.state,
            removeObjectHandler = Pair(planetState.planetDto.isFromLocalStore) {
                planetViewModel.removePlanet(planetState.planetDto.id)
                navController.navigate(Screen.Home.route)
            },
            content = { state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                if (showAddObjectBottomSheet) {
                    CopyModelBottomModal(modifier, onDismiss = { showAddObjectBottomSheet = false }) {
                        AddPlanetObject(modifier, planetState.planetDto
                                .copy(name = "${planetState.planetDto.name}-Copy"), addViewModel) { newId ->
                            scope.launch {
                                updateImages(planetViewModel.images) {
                                    planetViewModel.saveInDatabase(newId, it)
                                }
                                snackBarHostState.showSnackbar("Updated Movie ${planetState.planetDto.name}")
                            }
                            showAddObjectBottomSheet = false
                        }
                    }
                }
                PlanetScreenContent(state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, planetViewModel)
            }
    )
}

@Composable
fun PlanetScreenContent(
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        planetViewModel: PlanetViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by planetViewModel.images.collectAsState()
    val planetState by planetViewModel.selectedPlanet.collectAsState()
    val selected by remember { mutableStateOf(planetState.planetDto) }

    Box(modifier = modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImageAsset(selected.id, PLANETS, selected.isFromLocalStore,
                    LocalContext.current),
                    error = choosePainter(PLANETS),
                    contentDescription = stringResource(R.string.planets),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(PICTURE_HEIGHT)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(Color.Transparent))
            Text(text = selected.name,
                    fontFamily = SOLOFontName,
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(NAME_PLACEHOLDER_HEIGHT)
                            .basicMarquee(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
            )
            TripleInfoBox(
                    stringResource(R.string.diameter_box_name),
                    selected.diameter,
                    UnitName.KM,
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
                    UnitName.H,
                    stringResource(R.string.orbital_period_box_name),
                    selected.orbitalPeriod,
                    UnitName.DAYS,
                    halfWidth)
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.climates_box_name),
                        null, selected.climates,
                        width = thirdWidth)
                InfoBox(
                        stringResource(R.string.surface_water_box_name),
                        name = selected.surfaceWater,
                        unitName = UnitName.PERCENT,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.terrains_box_name),
                        null, selected.terrains,
                        width = thirdWidth)
            }
            ConnectionTitle(text = HEROES_NAME, shouldShowTitle = selected.characterConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.characterConnection, halfWidth, navController)
            ConnectionTitle(text = MOVIES_NAME, shouldShowTitle = selected.movieConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.movieConnection, halfWidth, navController)
            GallerySliderPart(imagesState, refreshScope, snackBarHostState, imagesStateRefresh) { imageSliderModel ->
                planetViewModel.deleteFromDatabase(imageSliderModel)
            }
        }
    }
}