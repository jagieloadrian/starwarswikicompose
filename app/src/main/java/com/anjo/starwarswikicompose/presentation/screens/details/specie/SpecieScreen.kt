package com.anjo.starwarswikicompose.presentation.screens.details.specie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
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
import com.anjo.GetSpecieQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.screens.common.*
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Category.PEOPLE
import com.anjo.starwarswikicompose.utils.Category.SPECIES
import com.anjo.starwarswikicompose.utils.getLocalWidth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpecieContentScreen(
        navController: NavHostController,
        specieViewModel: SpecieViewModel = hiltViewModel()
) {
    val selectedSpecie by specieViewModel.selectedSpecie.collectAsState()
    selectedSpecie?.let { SpecieVisualisation(it, navController) }
}

@ExperimentalFoundationApi
@Composable
private fun SpecieVisualisation(selectedSpecie: GetSpecieQuery.Species, navController: NavHostController) {
    val width = getLocalWidth()
    val halfWidth = (width / 2).dp
    val thirdWidth = (width / 3).dp
    val state = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()
            .paint(painter = painterResource(R.drawable.stars_image),
                    contentScale = ContentScale.FillBounds)) {
        Column(modifier = Modifier.verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = findImage(selectedSpecie.id, SPECIES),
                    error = choosePainter(SPECIES),
                    contentDescription = stringResource(R.string.species),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .height(PICTURE_HEIGHT)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(Color.Magenta))
            Text(text = selectedSpecie.name.orEmpty(),
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
                        stringResource(R.string.language_box_name),
                        selectedSpecie.language,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.homeworld_box_name),
                        selectedSpecie.homeworld?.name,
                        id = selectedSpecie.homeworld?.id,
                        category = Category.PLANETS,
                        width = halfWidth,
                        navController)
            }
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBox(
                        stringResource(R.string.classification_box_name),
                        selectedSpecie.classification,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.designation_box_name),
                        selectedSpecie.designation,
                        width = halfWidth)
            }
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBox(
                        stringResource(R.string.avr_height_box_name),
                        selectedSpecie.averageHeight,
                        width = halfWidth)
                InfoBox(
                        stringResource(R.string.avr_lifespan_box_name),
                        selectedSpecie.averageLifespan,
                        width = halfWidth)
            }
            Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                InfoBoxColumn(
                        stringResource(R.string.eye_colors_box_name),
                        null, selectedSpecie.eyeColors,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.hair_colors_box_name),
                        null, selectedSpecie.hairColors,
                        width = thirdWidth)
                InfoBoxColumn(
                        stringResource(R.string.skin_colors_box_name),
                        null, selectedSpecie.skinColors,
                        width = thirdWidth)
            }
            ShowCharacters(selectedSpecie, halfWidth, navController)
            ShowMovies(selectedSpecie, halfWidth, navController)
        }
    }
}

@Composable
private fun ShowCharacters(selectedSpecie: GetSpecieQuery.Species, halfWidth: Dp, navController: NavHostController) {
    val count = selectedSpecie.personConnection?.totalCount
    if (shouldInstanceLazyRow(selectedSpecie.personConnection,
                    count,
                    selectedSpecie.personConnection?.people)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedSpecie.personConnection!!.people!!) { item ->
                RelatedBox(item!!.id, item.name, PEOPLE, width = halfWidth, navController = navController)
            }
        }
    }
}

@Composable
private fun ShowMovies(selectedSpecie: GetSpecieQuery.Species, halfWidth: Dp, navController: NavHostController) {
    val count = selectedSpecie.filmConnection?.totalCount
    if (shouldInstanceLazyRow(selectedSpecie.filmConnection,
                    count,
                    selectedSpecie.filmConnection?.films)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = selectedSpecie.filmConnection!!.films!!) { item ->
                RelatedBox(item!!.id, item.title, Category.FILMS, width = halfWidth, navController = navController)
            }
        }
    }
}