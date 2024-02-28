package com.anjo.starwarswikicompose.presentation.screens.vehicle.detail

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
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.DoubleInfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.screens.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.screens.common.TripleInfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.Constants.DELETE_AND_REFRESH_IMAGES
import com.anjo.starwarswikicompose.utils.Constants.REFRESH_IMAGES
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun VehicleContentScreen(
        navController: NavHostController,
        vehicleViewModel: VehicleViewModel = hiltViewModel(),
) {
    val vehicleState by vehicleViewModel.selectedVehicle.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        vehicleViewModel.getVehicle()
        init.value = false
    }
    DetailVisualisationComponent(
            refreshImages = { vehicleViewModel.refreshImages(vehicleState.vehicle.id) },
            selectedName = vehicleState.vehicle.name,
            saveInDatabase = { vehicleViewModel.saveInDatabase(vehicleState.vehicle.id, it) },
            navController = navController,
            stateObject = vehicleState.state,
            content = { padding, state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                VehicleContentScreen(padding, state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, vehicleState.vehicle, vehicleViewModel)
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VehicleContentScreen(
        padding: PaddingValues,
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        selected: Vehicle,
        vehicleViewModel: VehicleViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by vehicleViewModel.images.collectAsState()

    Box(modifier = modifier.fillMaxSize()
            .padding(padding)
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImage(selected.id, VEHICLES),
                    error = choosePainter(VEHICLES),
                    contentDescription = stringResource(R.string.vehicles),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(VEHICLE_PICTURE_HEIGHT)
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
            DoubleInfoBox(
                    stringResource(R.string.model_box_name),
                    selected.model,
                    stringResource(R.string.vehicle_class_box_name),
                    selected.vehicleClass,
                    halfWidth = halfWidth
            )
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.manufacturers_box_name),
                        null, selected.manufacturers,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.cost_box_name),
                        selected.cost,
                        width = halfWidth)
            }
            TripleInfoBox(
                    stringResource(R.string.length_box_name),
                    selected.length,
                    stringResource(R.string.crew_box_name),
                    selected.crew,
                    stringResource(R.string.passengers_box_name),
                    selected.passengers,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.v_max_box_name),
                    selected.vMax,
                    stringResource(R.string.cargo_box_name),
                    selected.cargoCapacity,
                    stringResource(R.string.consumables_box_name),
                    selected.consumables,
                    thirdWidth = thirdWidth
            )
            ShowHorizontalBoxes(selected.characterConnection, Category.PEOPLE, halfWidth, navController)
            ShowHorizontalBoxes(selected.movieConnection, Category.FILMS, halfWidth, navController)
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
                        vehicleViewModel.deleteFromDatabase(it)
                        imagesStateRefresh.value = true
                    })
        }
    }
}