package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.*
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.presentation.screens.common.navigateToProperlyCompose
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.Constants.LIMIT_TEXT_IN_LINE

@Composable
fun <T> CommonButton(navController: NavHostController, item: T, category: Category) {
    Box(
            modifier = Modifier.fillMaxSize()
                    .clip(shape = RoundedCornerShape(35.dp))
                    .background(brush = Brush.linearGradient(listOf(
                            Color.Yellow, Color.Red, Color.Blue
                    )))
                    .clip(shape = RoundedCornerShape(50.dp))
                    .clickable { navigateToProperlyCompose(navController, item, category) },
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = findImage(item, category),
                    error = choosePainter(category),
                    contentDescription = stringResource(R.string.movies),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().weight(1f)
                            .padding(EXTRA_SMALL_PADDING)
                            .align(alignment = Alignment.CenterVertically)
                            .clip(CircleShape)
                            .background(Color.Magenta))
            ColumnText(item, category, modifier = Modifier.weight(4f))
        }
    }
}

@Composable
private fun <T> ColumnText(item: T, category: Category, modifier: Modifier) {
    return when (category) {
        Category.FILMS     -> FilmColumnText(item as GetAllFilmsQuery.Film, modifier)
        Category.PEOPLE    -> PeopleColumnText(item as GetAllPeoplesQuery.Person, modifier)
        Category.PLANETS   -> PlanetColumnText(item as GetAllPlanetsQuery.Planet, modifier)
        Category.SPECIES   -> SpecieColumnText(item as GetAllSpeciesQuery.Species, modifier)
        Category.STARSHIPS -> StarshipColumnText(item as GetAllStarshipsQuery.Starship, modifier)
        Category.VEHICLES  -> VehicleColumnText(item as GetAllVehiclesQuery.Vehicle, modifier)
    }
}

@Composable
fun FilmColumnText(item: GetAllFilmsQuery.Film, modifier: Modifier) {
    Row(modifier = modifier.fillMaxSize()) {
        Text(text = item.title.toString(),
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName)
        Text(text = item.episodeID.toString(),
                modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName)
    }
}

@Composable
fun PeopleColumnText(item: GetAllPeoplesQuery.Person?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(text = item?.birthYear.toString())
    }
}

fun formatPopulation(population: Double?): String {
    if (population == null) {
        return "0 citizens"
    }
    return "$population citizens"
}

@Composable
fun PlanetColumnText(item: GetAllPlanetsQuery.Planet?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(text = (formatPopulation(item?.population)))
    }
}

@Composable
fun SpecieColumnText(item: GetAllSpeciesQuery.Species?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(text = item?.language.toString())
    }
}

@Composable
fun StarshipColumnText(item: GetAllStarshipsQuery.Starship?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(
                text = item?.model.toString().take(LIMIT_TEXT_IN_LINE),
        )
    }
}

@Composable
fun VehicleColumnText(item: GetAllVehiclesQuery.Vehicle?, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item?.name.toString(),
                fontWeight = FontWeight.ExtraBold)
        Text(text = item?.model.toString().take(LIMIT_TEXT_IN_LINE))
    }
}
