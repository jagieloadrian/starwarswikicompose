package com.anjo.starwarswikicompose.presentation.screens.details.person

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
import com.anjo.GetPersonQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.screens.common.CustomBottomAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.CustomTopAppBar
import com.anjo.starwarswikicompose.presentation.screens.common.InfoBox
import com.anjo.starwarswikicompose.presentation.screens.common.RelatedBox
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.clickableArrangementInLazyRow
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.presentation.screens.common.shouldInstanceLazyRow
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category.FILMS
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.PLANETS
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.Category.STARSHIPS
import com.anjo.starwarswikicompose.utils.Category.VEHICLES
import com.anjo.starwarswikicompose.utils.getLocalWidth

@Composable
fun PersonContentScreen(
        navController: NavHostController,
        personViewModel: PersonViewModel = hiltViewModel()
) {
    val selectedPerson by personViewModel.selectedPerson.collectAsState()
    selectedPerson?.let { PersonVisualisation(it, navController) }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PersonVisualisation(
        selectedPerson: GetPersonQuery.Person,
        navController: NavHostController) {
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
                AsyncImage(model = findImage(selectedPerson.id, PEOPLE),
                        error = choosePainter(PEOPLE),
                        contentDescription = stringResource(R.string.people),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                                .height(PICTURE_HEIGHT)
                                .align(alignment = Alignment.CenterHorizontally)
                                .clip(CircleShape)
                                .background(Color.Magenta))
                Text(text = selectedPerson.name.orEmpty(),
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
                        horizontalArrangement = Arrangement.SpaceAround) {
                    InfoBox(
                            stringResource(R.string.homeworld_box_name),
                            selectedPerson.homeworld?.name,
                            id = selectedPerson.homeworld?.id,
                            category = PLANETS,
                            width = halfWidth,
                            navController)
                    InfoBox(
                            stringResource(R.string.species_box_name),
                            selectedPerson.species?.name,
                            id = selectedPerson.species?.id,
                            category = SPECIES,
                            width = halfWidth,
                            navController)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.birth_box_name),
                            selectedPerson.birthYear,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.height_box_name),
                            selectedPerson.height,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.mass_box_name),
                            selectedPerson.mass,
                            width = thirdWidth)
                }
                Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoBox(
                            stringResource(R.string.gender_box_name),
                            selectedPerson.gender,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.hair_box_name),
                            selectedPerson.hairColor,
                            width = thirdWidth)
                    InfoBox(
                            stringResource(R.string.skin_box_name),
                            selectedPerson.skinColor,
                            width = thirdWidth)
                }
                ShowMovies(selectedPerson, halfWidth, navController)
                ShowStarships(selectedPerson, halfWidth, navController)
                ShowVehicles(selectedPerson, halfWidth, navController)
            }
        }
    }
}

@Composable
private fun ShowMovies(selectedPerson: GetPersonQuery.Person, halfWidth: Dp, navController: NavHostController) {
    val count = selectedPerson.filmConnection?.totalCount
    if (shouldInstanceLazyRow(selectedPerson.filmConnection,
                    count,
                    selectedPerson.filmConnection?.films)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedPerson.filmConnection!!.films!!) { item ->
                RelatedBox(item!!.id, item.title, FILMS, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowStarships(selectedPerson: GetPersonQuery.Person, halfWidth: Dp, navController: NavHostController) {
    val count = selectedPerson.starshipConnection?.totalCount
    if (shouldInstanceLazyRow(selectedPerson.starshipConnection,
                    count,
                    selectedPerson.starshipConnection?.starships)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedPerson.starshipConnection!!.starships!!) { item ->
                RelatedBox(item!!.id, item.name, STARSHIPS, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowVehicles(selectedPerson: GetPersonQuery.Person, halfWidth: Dp, navController: NavHostController) {
    val count = selectedPerson.vehicleConnection?.totalCount
    if (shouldInstanceLazyRow(selectedPerson.vehicleConnection,
                    count,
                    selectedPerson.vehicleConnection?.vehicles)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedPerson.vehicleConnection!!.vehicles!!) { item ->
                RelatedBox(item!!.id, item.name, VEHICLES, width = halfWidth, navController = navController)
            }
        }
    }
}