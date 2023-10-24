package com.anjo.starwarswikicompose.presentation.screens.starship.detail

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.anjo.GetStarshipQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.GallerySlider
import com.anjo.starwarswikicompose.presentation.screens.common.AddImageFab
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
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.getLocalWidth


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarshipContentScreen(
        navController: NavHostController,
        starshipViewModel: StarshipViewModel = hiltViewModel()
) {
    val selectedStarship by starshipViewModel.selectedStarship.collectAsState()
    selectedStarship?.let { StarshipVisualisation(it, navController, starshipViewModel) }
}

@ExperimentalFoundationApi
@Composable
private fun StarshipVisualisation(selected: GetStarshipQuery.Starship, navController: NavHostController,
                                  starshipViewModel: StarshipViewModel) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()
    var fabExtended by remember { mutableStateOf(true) }
    val images = starshipViewModel.images.collectAsState()
    val clipManager = LocalClipboardManager.current

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
                AddImageFab(extended = fabExtended) {
                    val photoUrl = clipManager.getText()?.text
                    photoUrl?.let { starshipViewModel.saveInDatabase(selected, it) }
                }
            }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
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
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.cost_box_name),
                            selected.costInCredits,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.length_box_name),
                            selected.length,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.cargo_box_name),
                            selected.cargoCapacity,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.v_max_box_name),
                            selected.maxAtmospheringSpeed,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.hyperdrive_box_name),
                            selected.hyperdriveRating,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.mglt_box_name),
                            selected.MGLT,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.crew_box_name),
                            selected.crew,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.passengers_box_name),
                            selected.passengers,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.consumables_box_name),
                            selected.consumables,
                            width = thirdWidth)
                }
                ShowPilots(selected, halfWidth, navController)
                ShowMovies(selected, halfWidth, navController)
                if (images.value.isNotEmpty()) {
                    GallerySlider(images = images.value)
                }
            }
        }
    }
}

@Composable
private fun ShowPilots(selectedStarship: GetStarshipQuery.Starship, halfWidth: Dp, navController: NavHostController) {
    val count = selectedStarship.pilotConnection?.totalCount
    if (shouldInstanceLazyRow(selectedStarship.pilotConnection,
                    count,
                    selectedStarship.pilotConnection?.pilots)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedStarship.pilotConnection!!.pilots!!) { item ->
                RelatedBox(item!!.id, item.name, Category.PEOPLE, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowMovies(selectedStarship: GetStarshipQuery.Starship, halfWidth: Dp, navController: NavHostController) {
    val count = selectedStarship.filmConnection?.totalCount
    if (shouldInstanceLazyRow(selectedStarship.filmConnection,
                    selectedStarship.filmConnection?.totalCount,
                    selectedStarship.filmConnection?.films)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedStarship.filmConnection!!.films!!) { item ->
                RelatedBox(item!!.id, item.title, Category.FILMS, width = halfWidth, navController = navController)
            }
        }
    }
}