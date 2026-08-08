package com.anjo.starwarswikicompose.presentation.screens.specie

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
import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.ConnectionTitle
import com.anjo.starwarswikicompose.presentation.common.GallerySliderPart
import com.anjo.starwarswikicompose.presentation.common.create.models.AddSpecieObject
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.common.detail.DoubleInfoBox
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBox
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.common.detail.ShowHorizontalBoxes
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
import com.anjo.starwarswikicompose.utils.TestTags.SPECIE_SCREEN_TAG
import com.anjo.starwarswikicompose.utils.updateImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SpecieContentScreen(
        navController: NavHostController,
        specieViewModel: SpecieViewModel = hiltViewModel(),
        addViewModel: AddObjectViewModel = hiltViewModel()) {
    val specieState by specieViewModel.selectedSpecie.collectAsState()
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        specieViewModel.getSpecie()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshObject = { specieViewModel.getSpecie(); specieViewModel.refreshImages(specieState.specieDto.id) },
            saveImageInDatabase = {
                specieViewModel.saveInDatabase(specieState.specieDto.id, it); specieViewModel.getSpecie()
            },
            updateObjectFab = { showAddObjectBottomSheet = !showAddObjectBottomSheet },
            navController = navController,
            stateObject = specieState.state,
            removeObjectHandler = Pair(specieState.specieDto.isFromLocalStore) {
                specieViewModel.removeSpecie(specieState.specieDto.id)
                navController.navigate(Screen.Home.route)
            },
            content = { state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                if (showAddObjectBottomSheet) {
                    CopyModelBottomModal(modifier, onDismiss = { showAddObjectBottomSheet = false }) {
                        AddSpecieObject(modifier, specieState.specieDto
                                .copy(name = "${specieState.specieDto.name}-Copy"), addViewModel) { newId ->
                            scope.launch {
                                updateImages(specieViewModel.images) {
                                    specieViewModel.saveInDatabase(newId, it)
                                }
                                snackBarHostState.showSnackbar("Updated Movie ${specieState.specieDto.name}")
                            }
                            showAddObjectBottomSheet = false
                        }
                    }
                }
                SpecieScreenContent(state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, specieViewModel)
            }
    )
}

@Composable
fun SpecieScreenContent(
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        specieViewModel: SpecieViewModel,
) {

    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by specieViewModel.images.collectAsState()
    val specieState by specieViewModel.selectedSpecie.collectAsState()
    val selected by remember { mutableStateOf(specieState.specieDto) }

    Box(modifier = modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)
            .testTag(SPECIE_SCREEN_TAG)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImageAsset(selected.id, SPECIES, selected.isFromLocalStore,
                    LocalContext.current),
                    error = choosePainter(SPECIES),
                    contentDescription = stringResource(R.string.species),
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
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBox(
                        stringResource(R.string.language_box_name),
                        name = selected.language,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.homeworld_box_name),
                        selected.homeworld,
                        width = halfWidth,
                        navController = navController)
            }
            DoubleInfoBox(stringResource(R.string.classification_box_name),
                    selected.classification, null,
                    stringResource(R.string.designation_box_name),
                    selected.designation, null,
                    halfWidth)
            DoubleInfoBox(stringResource(R.string.avr_height_box_name),
                    selected.averageHeight, UnitName.CM,
                    stringResource(R.string.avr_lifespan_box_name),
                    selected.averageLifespan, UnitName.YEARS,
                    halfWidth)
            Row(modifier = Modifier
                    .height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.eye_colors_box_name),
                        null, selected.eyeColors,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.hair_colors_box_name),
                        null, selected.hairColors,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.skin_colors_box_name),
                        null, selected.skinColors,
                        width = thirdWidth)
            }
            ConnectionTitle(text = HEROES_NAME, shouldShowTitle = selected.characterConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.characterConnection, halfWidth, navController)
            ConnectionTitle(text = MOVIES_NAME, shouldShowTitle = selected.movieConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.movieConnection, halfWidth, navController)
            GallerySliderPart(imagesState, refreshScope, snackBarHostState, imagesStateRefresh) { imageSliderModel ->
                specieViewModel.deleteFromDatabase(imageSliderModel)
            }
        }
    }
}