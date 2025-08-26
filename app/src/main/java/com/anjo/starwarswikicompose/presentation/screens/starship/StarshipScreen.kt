package com.anjo.starwarswikicompose.presentation.screens.starship

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
import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.ConnectionTitle
import com.anjo.starwarswikicompose.presentation.common.GallerySliderPart
import com.anjo.starwarswikicompose.presentation.common.create.models.AddStarshipObject
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
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
import com.anjo.starwarswikicompose.utils.updateImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StarshipContentScreen(
        navController: NavHostController,
        starshipViewModel: StarshipViewModel = hiltViewModel(),
        addViewModel: AddObjectViewModel = hiltViewModel()) {
    val starshipState by starshipViewModel.selectedStarship.collectAsState()
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        starshipViewModel.getStarship()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshObject = {
                starshipViewModel.getStarship(); starshipViewModel.refreshImages(starshipState.starshipDto.id)
            },
            saveImageInDatabase = {
                starshipViewModel.saveInDatabase(starshipState.starshipDto.id, it); starshipViewModel.getStarship()
            },
            updateObjectFab = { showAddObjectBottomSheet = !showAddObjectBottomSheet },
            navController = navController,
            stateObject = starshipState.state,
            removeObjectHandler = Pair(starshipState.starshipDto.isFromLocalStore) {
                starshipViewModel.removeStarship(starshipState.starshipDto.id)
                navController.navigate(Screen.Home.route)
            },
            content = { state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                if (showAddObjectBottomSheet) {
                    CopyModelBottomModal(modifier, onDismiss = { showAddObjectBottomSheet = false }) {
                        AddStarshipObject(modifier, starshipState.starshipDto
                                .copy(name = "${starshipState.starshipDto.name}-Copy"), addViewModel) { newId ->
                            scope.launch {
                                updateImages(starshipViewModel.images) {
                                    starshipViewModel.saveInDatabase(newId, it)
                                }
                                snackBarHostState.showSnackbar("Updated Movie ${starshipState.starshipDto.name}")
                            }
                            showAddObjectBottomSheet = false
                            starshipViewModel.getStarship()
                        }
                    }
                }
                StarshipContentScreen(state, scope, snackBarHostState, imagesStateRefresh, modifier,
                        navController, starshipViewModel)
            }
    )
}

@Composable
fun StarshipContentScreen(
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        starshipViewModel: StarshipViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by starshipViewModel.images.collectAsState()
    val starshipState by starshipViewModel.selectedStarship.collectAsState()
    val selected by remember { mutableStateOf(starshipState.starshipDto) }

    Box(modifier = modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImageAsset(selected.id, STARSHIPS, selected.isFromLocalStore,
                    LocalContext.current),
                    error = choosePainter(STARSHIPS),
                    contentDescription = stringResource(R.string.starships),
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
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBox(
                        stringResource(R.string.model_box_name),
                        name = selected.model,
                        width = thirdWidth)
                InfoBox(
                        stringResource(R.string.starship_class_box_name),
                        name = selected.starshipClass,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.manufacturers_box_name),
                        null, selected.manufacturers,
                        width = thirdWidth)
            }
            TripleInfoBox(
                    stringResource(R.string.cost_box_name),
                    selected.cost, UnitName.CREDITS,
                    stringResource(R.string.length_box_name),
                    selected.length, UnitName.M,
                    stringResource(R.string.cargo_box_name),
                    selected.cargoCapacity, UnitName.KG,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.v_max_box_name),
                    selected.vMax, UnitName.KMPERHOUR,
                    stringResource(R.string.hyperdrive_box_name),
                    selected.hyperdriveRating, UnitName.CLASS,
                    stringResource(R.string.mglt_box_name),
                    selected.megalight, UnitName.PERHOUR,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.crew_box_name),
                    selected.crew, UnitName.MEN,
                    stringResource(R.string.passengers_box_name),
                    selected.passengers, UnitName.MEN,
                    stringResource(R.string.consumables_box_name),
                    selected.consumables, null,
                    thirdWidth = thirdWidth
            )
            ConnectionTitle(text = HEROES_NAME, shouldShowTitle = selected.characterConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.characterConnection, halfWidth, navController)
            ConnectionTitle(text = MOVIES_NAME, shouldShowTitle = selected.movieConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.movieConnection, halfWidth, navController)
            GallerySliderPart(imagesState, refreshScope, snackBarHostState, imagesStateRefresh) { imageSliderModel ->
                starshipViewModel.deleteFromDatabase(imageSliderModel)
            }
        }
    }
}