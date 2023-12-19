package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.GetAllFilmsQuery
import com.anjo.starwarswikicompose.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.screens.common.choosePainter
import com.anjo.starwarswikicompose.presentation.screens.common.findImage
import com.anjo.starwarswikicompose.presentation.screens.movie.home.FilmColumnText
import com.anjo.starwarswikicompose.presentation.screens.person.home.PeopleColumnText
import com.anjo.starwarswikicompose.presentation.screens.planet.home.PlanetColumnText
import com.anjo.starwarswikicompose.presentation.screens.specie.home.SpecieColumnText
import com.anjo.starwarswikicompose.presentation.screens.starship.home.StarshipColumnText
import com.anjo.starwarswikicompose.presentation.screens.vehicle.home.VehicleColumnText
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.navigateToProperlyCompose

@Composable
fun <T> CommonButton(navController: NavHostController, item: T, category: Category) {
    Box(
            modifier = Modifier.fillMaxSize()
                    .clip(shape = RoundedCornerShape(35.dp))
                    .background(brush = Brush.linearGradient(listOf(
                            Color.Yellow, Color.Red, Color.Blue
                    )))
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

fun formatPopulation(population: Double?): String {
    if (population == null) {
        return "0 citizens"
    }
    return "$population citizens"
}
