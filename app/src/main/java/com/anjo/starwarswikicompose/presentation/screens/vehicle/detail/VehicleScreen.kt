package com.anjo.starwarswikicompose.presentation.screens.vehicle.detail

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.GetVehicleQuery
import com.anjo.starwarswikicompose.R
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
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Category.VEHICLES
import com.anjo.starwarswikicompose.utils.getLocalWidth


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VehicleContentScreen(
        navController: NavHostController,
        vehicleViewModel: VehicleViewModel = hiltViewModel()
) {
    val selectedVehicle by vehicleViewModel.selectedVehicle.collectAsState()
    selectedVehicle?.let { VehicleVisualisation(it, navController) }
}

@ExperimentalFoundationApi
@Composable
fun VehicleVisualisation(selectedVehicle: GetVehicleQuery.Vehicle, navController: NavHostController) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()

    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()
                .padding(padding)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = findImage(selectedVehicle.id, VEHICLES),
                        error = choosePainter(VEHICLES),
                        contentDescription = stringResource(R.string.vehicles),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                                .height(VEHICLE_PICTURE_HEIGHT)
                                .align(alignment = Alignment.CenterHorizontally)
                                .clip(CircleShape)
                                .background(Color.Magenta))
                Text(text = selectedVehicle.name.orEmpty(),
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
                            stringResource(R.string.model_box_name),
                            selectedVehicle.model,
                            width = halfWidth)
                    InfoBox(
                            stringResource(R.string.vehicle_class_box_name),
                            selectedVehicle.vehicleClass,
                            width = halfWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBoxColumn(
                            stringResource(R.string.manufacturers_box_name),
                            null, selectedVehicle.manufacturers,
                            width = halfWidth)
                    InfoBox(
                            stringResource(R.string.cost_box_name),
                            selectedVehicle.costInCredits,
                            width = halfWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.length_box_name),
                            selectedVehicle.length,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.crew_box_name),
                            selectedVehicle.crew,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.passengers_box_name),
                            selectedVehicle.passengers,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.v_max_box_name),
                            selectedVehicle.maxAtmospheringSpeed,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.cargo_box_name),
                            selectedVehicle.cargoCapacity,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.consumables_box_name),
                            selectedVehicle.consumables,
                            width = thirdWidth)
                }
                ShowPilots(selectedVehicle, halfWidth, navController)
                ShowMovies(selectedVehicle, halfWidth, navController)
            }
        }
    }
}

@Composable
private fun ShowPilots(selectedVehicle: GetVehicleQuery.Vehicle, halfWidth: Dp, navController: NavHostController) {
    val count = selectedVehicle.pilotConnection?.totalCount
    if (shouldInstanceLazyRow(selectedVehicle.pilotConnection,
                    count,
                    selectedVehicle.pilotConnection?.pilots)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedVehicle.pilotConnection!!.pilots!!) { item ->
                RelatedBox(item!!.id, item.name, Category.PEOPLE, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowMovies(selectedVehicle: GetVehicleQuery.Vehicle, halfWidth: Dp, navController: NavHostController) {
    val count = selectedVehicle.filmConnection?.totalCount
    if (shouldInstanceLazyRow(selectedVehicle.filmConnection,
                    selectedVehicle.filmConnection?.totalCount,
                    selectedVehicle.filmConnection?.films)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedVehicle.filmConnection!!.films!!) { item ->
                RelatedBox(item!!.id, item.title, Category.FILMS, width = halfWidth, navController = navController)
            }
        }
    }
}