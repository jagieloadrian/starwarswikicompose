package com.anjo.starwarswikicompose.presentation.screens.starship.detail

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
import com.anjo.starwarswikicompose.domain.model.Unit
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.common.InfoBox
import com.anjo.starwarswikicompose.presentation.common.InfoBoxColumn
import com.anjo.starwarswikicompose.presentation.common.ShowHorizontalBoxes
import com.anjo.starwarswikicompose.presentation.common.TripleInfoBox
import com.anjo.starwarswikicompose.presentation.common.choosePainter
import com.anjo.starwarswikicompose.presentation.common.detail.DetailVisualisationComponent
import com.anjo.starwarswikicompose.presentation.common.findImage
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.getLocalWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StarshipContentScreen(
        navController: NavHostController,
        starshipViewModel: StarshipViewModel = hiltViewModel(),
) {
    val starshipState by starshipViewModel.selectedStarship.collectAsState()
    val init = remember { mutableStateOf(true) }

    if (init.value) {
        starshipViewModel.getStarship()
        init.value = false
    }

    DetailVisualisationComponent(
            refreshImages = { starshipViewModel.refreshImages(starshipState.starship.id) },
            selectedName = starshipState.starship.name,
            saveInDatabase = { starshipViewModel.saveInDatabase(starshipState.starship.id, it) },
            navController = navController,
            stateObject = starshipState.state,
            content = { padding, state, scope, snackBarHostState, imagesStateRefresh, modifier ->
                StarshipContentScreen(padding, state, scope,
                        snackBarHostState, imagesStateRefresh, modifier,
                        navController, starshipState.starship, starshipViewModel)
            }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarshipContentScreen(
        padding: PaddingValues,
        state: ScrollState,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        imagesStateRefresh: MutableState<Boolean>,
        modifier: Modifier,
        navController: NavHostController,
        selected: Starship,
        starshipViewModel: StarshipViewModel,
) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val imagesState by starshipViewModel.images.collectAsState()

    Box(modifier = modifier.fillMaxSize().padding(padding)
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImage(selected.id, STARSHIPS),
                    error = choosePainter(STARSHIPS),
                    contentDescription = stringResource(R.string.starships),
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
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBox(
                        stringResource(R.string.model_box_name),
                        selected.model,
                        width = thirdWidth)
                InfoBox(
                        stringResource(R.string.starship_class_box_name),
                        selected.starshipClass,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.manufacturers_box_name),
                        null, selected.manufacturers,
                        width = thirdWidth)
            }
            TripleInfoBox(
                    stringResource(R.string.cost_box_name),
                    selected.cost, Unit.CREDITS,
                    stringResource(R.string.length_box_name),
                    selected.length, Unit.M,
                    stringResource(R.string.cargo_box_name),
                    selected.cargoCapacity, Unit.KG,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.v_max_box_name),
                    selected.vMax, Unit.KMPERHOUR,
                    stringResource(R.string.hyperdrive_box_name),
                    selected.hyperdriveRating, Unit.CLASS,
                    stringResource(R.string.mglt_box_name),
                    selected.megalight, Unit.PERHOUR,
                    thirdWidth = thirdWidth
            )
            TripleInfoBox(
                    stringResource(R.string.crew_box_name),
                    selected.crew,Unit.MEN,
                    stringResource(R.string.passengers_box_name),
                    selected.passengers,Unit.MEN,
                    stringResource(R.string.consumables_box_name),
                    selected.consumables, null,
                    thirdWidth = thirdWidth
            )
            ShowHorizontalBoxes(selected.characterConnection, Category.PEOPLE, halfWidth, navController)
            ShowHorizontalBoxes(selected.movieConnection, Category.FILMS, halfWidth, navController)
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
                        starshipViewModel.deleteFromDatabase(it)
                        imagesStateRefresh.value = true
                    })
        }
    }
}