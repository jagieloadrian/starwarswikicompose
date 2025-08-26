package com.anjo.starwarswikicompose.presentation.screens.person

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
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.ConnectionTitle
import com.anjo.starwarswikicompose.presentation.common.GallerySliderPart
import com.anjo.starwarswikicompose.presentation.common.create.models.AddPersonObject
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.common.detail.InfoBox
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
import com.anjo.starwarswikicompose.utils.Constants.MOVIES_NAME
import com.anjo.starwarswikicompose.utils.Constants.STARSHIPS_NAME
import com.anjo.starwarswikicompose.utils.Constants.VEHICLES_NAME
import com.anjo.starwarswikicompose.utils.updateImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PersonContentScreen(
        navController: NavHostController,
        personViewModel: PersonViewModel = hiltViewModel(),
        addViewModel: AddObjectViewModel = hiltViewModel()) {
    val personState by personViewModel.selectedPerson.collectAsState()
    val init = remember { mutableStateOf(true) }
    var showAddObjectBottomSheet by remember { mutableStateOf(false) }

    if (init.value) {
        personViewModel.getPerson()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshObject = { personViewModel.getPerson(); personViewModel.refreshImages(personState.personDto.id) },
            saveImageInDatabase = {
                personViewModel.saveInDatabase(personState.personDto.id, it); personViewModel.getPerson()
            },
            updateObjectFab = { showAddObjectBottomSheet = !showAddObjectBottomSheet },
            navController = navController,
            stateObject = personState.state,
            removeObjectHandler = Pair(personState.personDto.isFromLocalStore) {
                personViewModel.removePerson(personState.personDto.id)
                navController.navigate(Screen.Home.route)
            },
            content = { state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                if (showAddObjectBottomSheet) {
                    CopyModelBottomModal(modifier, onDismiss = { showAddObjectBottomSheet = false }) {
                        AddPersonObject(modifier, personState.personDto
                                .copy(name = "${personState.personDto.name}-Copy"), addViewModel) { newId ->
                            scope.launch {
                                updateImages(personViewModel.images) {
                                    personViewModel.saveInDatabase(newId, it)
                                }
                                snackBarHostState.showSnackbar("Updated Movie ${personState.personDto.name}")
                            }
                            showAddObjectBottomSheet = false
                        }
                    }
                }
                PersonScreenContent(state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, personViewModel)
            }
    )
}

@Composable
fun PersonScreenContent(
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        personViewModel: PersonViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by personViewModel.images.collectAsState()
    val personState by personViewModel.selectedPerson.collectAsState()
    val selected by remember { mutableStateOf(personState.personDto) }

    Box(modifier = modifier
            .fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(model = findImageAsset(selected.id, PEOPLE, selected.isFromLocalStore,
                    LocalContext.current),
                    error = choosePainter(PEOPLE),
                    contentDescription = stringResource(R.string.people),
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
                    horizontalArrangement = Arrangement.SpaceAround) {
                InfoBox(
                        stringResource(R.string.homeworld_box_name),
                        selected.homeworld,
                        width = halfWidth,
                        navController = navController)
                InfoBox(
                        stringResource(R.string.species_box_name),
                        selected.specie,
                        width = halfWidth,
                        navController = navController)
            }
            TripleInfoBox(
                    stringResource(R.string.birth_box_name),
                    selected.birthYear,
                    firstUnitName = null,
                    stringResource(R.string.height_box_name),
                    selected.height,
                    secondUnitName = UnitName.CM,
                    stringResource(R.string.mass_box_name),
                    selected.mass,
                    thirdUnitName = UnitName.KG,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.gender_box_name),
                    selected.gender,
                    null,
                    stringResource(R.string.hair_box_name),
                    selected.hair,
                    null,
                    stringResource(R.string.skin_box_name),
                    selected.skin,
                    null,
                    thirdWidth = thirdWidth
            )
            ConnectionTitle(text = MOVIES_NAME, shouldShowTitle = selected.movieConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.movieConnection, halfWidth, navController)
            ConnectionTitle(text = STARSHIPS_NAME, shouldShowTitle = selected.starshipConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.starshipConnection, halfWidth, navController)
            ConnectionTitle(text = VEHICLES_NAME, shouldShowTitle = selected.vehicleConnection.totalCount > 0)
            ShowHorizontalBoxes(selected.vehicleConnection, halfWidth, navController)
            GallerySliderPart(imagesState, refreshScope, snackBarHostState, imagesStateRefresh) { imageSliderModel ->
                personViewModel.deleteFromDatabase(imageSliderModel)
            }
        }
    }
}