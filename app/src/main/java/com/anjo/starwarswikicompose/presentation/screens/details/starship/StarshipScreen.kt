package com.anjo.starwarswikicompose.presentation.screens.details.starship

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
import com.anjo.GetStarshipQuery
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
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.getLocalWidth


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarshipContentScreen(
        navController: NavHostController,
        starshipViewModel: StarshipViewModel = hiltViewModel()
) {
    val selectedStarship by starshipViewModel.selectedStarship.collectAsState()
    selectedStarship?.let { StarshipVisualisation(it, navController) }
}

@ExperimentalFoundationApi
@Composable
private fun StarshipVisualisation(selectedStarship: GetStarshipQuery.Starship, navController: NavHostController) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()
    Scaffold(
            topBar = { CustomTopAppBar(navController) },
            bottomBar = { CustomBottomAppBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
                .paint(painter = painterResource(R.drawable.stars_image),
                        contentScale = ContentScale.FillBounds)) {
            Column(modifier = Modifier.verticalScroll(state),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = findImage(selectedStarship.id, STARSHIPS),
                        error = choosePainter(STARSHIPS),
                        contentDescription = stringResource(R.string.starships),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                                .height(VEHICLE_PICTURE_HEIGHT)
                                .align(alignment = Alignment.CenterHorizontally)
                                .clip(CircleShape)
                                .background(Color.Magenta))
                Text(text = selectedStarship.name.orEmpty(),
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
                            selectedStarship.model,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.starship_class_box_name),
                            selectedStarship.starshipClass,
                            width = thirdWidth)
                    InfoBoxColumn(
                            stringResource(R.string.manufacturers_box_name),
                            null, selectedStarship.manufacturers,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.cost_box_name),
                            selectedStarship.costInCredits,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.length_box_name),
                            selectedStarship.length,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.cargo_box_name),
                            selectedStarship.cargoCapacity,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.v_max_box_name),
                            selectedStarship.maxAtmospheringSpeed,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.hyperdrive_box_name),
                            selectedStarship.hyperdriveRating,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.mglt_box_name),
                            selectedStarship.MGLT,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.crew_box_name),
                            selectedStarship.crew,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.passengers_box_name),
                            selectedStarship.passengers,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.consumables_box_name),
                            selectedStarship.consumables,
                            width = thirdWidth)
                }
                ShowPilots(selectedStarship, halfWidth, navController)
                ShowMovies(selectedStarship, halfWidth, navController)
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