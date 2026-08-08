package com.anjo.starwarswikicompose.presentation.screens.vehicle

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.UnitName.CREDITS
import com.anjo.starwarswikicompose.domain.model.UnitName.KG
import com.anjo.starwarswikicompose.domain.model.UnitName.KMPERHOUR
import com.anjo.starwarswikicompose.domain.model.UnitName.M
import com.anjo.starwarswikicompose.domain.model.UnitName.MEN
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.ConnectionTitle
import com.anjo.starwarswikicompose.presentation.common.GallerySliderPart
import com.anjo.starwarswikicompose.presentation.common.create.models.AddVehicleObject
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
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.Constants.HEROES_NAME
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.TestTags.VEHICLE_SCREEN_TAG
import com.anjo.starwarswikicompose.utils.updateImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun VehicleContentScreen(
        navController: NavHostController,
        vehicleViewModel: VehicleViewModel = hiltViewModel(),
        addViewModel: AddObjectViewModel = hiltViewModel()) {
    val vehicleState by vehicleViewModel.selectedVehicle.collectAsState()
    val init = remember { mutableStateOf(true) }
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }
    if (init.value) {
        vehicleViewModel.getVehicle()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshObject = {
                vehicleViewModel.getVehicle(); vehicleViewModel.refreshImages(vehicleState.vehicleDto.id)
            },
            saveImageInDatabase = {
                vehicleViewModel.saveInDatabase(vehicleState.vehicleDto.id, it); vehicleViewModel.getVehicle()
            },
            updateObjectFab = { showAddObjectBottomSheet = !showAddObjectBottomSheet },
            navController = navController,
            stateObject = vehicleState.state,
            removeObjectHandler = Pair(vehicleState.vehicleDto.isFromLocalStore) {
                vehicleViewModel.removeVehicle(vehicleState.vehicleDto.id)
                navController.navigate(Screen.Home.route)
            },
            content = { state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                if (showAddObjectBottomSheet) {
                    CopyModelBottomModal(modifier, onDismiss = { showAddObjectBottomSheet = false }) {
                        AddVehicleObject(modifier, vehicleState.vehicleDto
                                .copy(name = "${vehicleState.vehicleDto.name}-Copy"), addViewModel) { newId ->
                            scope.launch {
                                updateImages(vehicleViewModel.images) {
                                    vehicleViewModel.saveInDatabase(newId, it)
                                }
                                snackBarHostState.showSnackbar("Updated Movie ${vehicleState.vehicleDto.name}")
                            }
                            showAddObjectBottomSheet = false
                            vehicleViewModel.getVehicle()
                        }
                    }
                }
                VehicleContentScreen(state, scope, snackBarHostState, imagesStateRefresh, modifier,
                        navController, vehicleViewModel)
            }
    )
}

@Composable
fun VehicleContentScreen(
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        vehicleViewModel: VehicleViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by vehicleViewModel.images.collectAsState()
    val vehicleState by vehicleViewModel.selectedVehicle.collectAsState()
    val selected by remember { mutableStateOf(vehicleState.vehicleDto) }

    Box(modifier = modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)
            .testTag(VEHICLE_SCREEN_TAG)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImageAsset(selected.id, VEHICLES, selected.isFromLocalStore,
                    LocalContext.current),
                    error = choosePainter(VEHICLES),
                    contentDescription = stringResource(R.string.vehicles),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(VEHICLE_PICTURE_HEIGHT)
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
            DoubleInfoBox(
                    stringResource(R.string.model_box_name),
                    selected.model, null,
                    stringResource(R.string.vehicle_class_box_name),
                    selected.vehicleClass, null,
                    halfWidth = halfWidth
            )
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.manufacturers_box_name),
                        null, selected.manufacturers,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.cost_box_name),
                        name = selected.cost, unitName = CREDITS,
                        width = halfWidth)
            }
            TripleInfoBox(
                    stringResource(R.string.length_box_name),
                    selected.length, M,
                    stringResource(R.string.crew_box_name),
                    selected.crew, MEN,
                    stringResource(R.string.passengers_box_name),
                    selected.passengers, MEN,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.v_max_box_name),
                    selected.vMax, KMPERHOUR,
                    stringResource(R.string.cargo_box_name),
                    selected.cargoCapacity, KG,
                    stringResource(R.string.consumables_box_name),
                    selected.consumables, null,
                    thirdWidth = thirdWidth
            )
            ConnectionTitle(text = HEROES_NAME, shouldShowTitle = selected.characterConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.characterConnection, halfWidth, navController)
            ConnectionTitle(text = MOVIES_NAME, shouldShowTitle = selected.movieConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.movieConnection, halfWidth, navController)
            GallerySliderPart(imagesState, refreshScope, snackBarHostState, imagesStateRefresh) { imageSliderModel ->
                vehicleViewModel.deleteFromDatabase(imageSliderModel)
            }
        }
    }
}