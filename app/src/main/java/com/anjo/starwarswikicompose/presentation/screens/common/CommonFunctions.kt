package com.anjo.starwarswikicompose.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.CLICKABLE_BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.RELATED_BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING_FOR_INFOBOX
import com.anjo.starwarswikicompose.utils.Category
import com.anjo.starwarswikicompose.utils.calculatePathToImage

@Composable
fun choosePainter(category: Category): Painter {
    return when (category) {
        Category.FILMS     -> painterResource(R.drawable.outline_movie_creation_24)
        Category.PEOPLE    -> painterResource(R.drawable.face)
        Category.PLANETS   -> painterResource(R.drawable.public_icon)
        Category.SPECIES   -> painterResource(R.drawable.genetics)
        Category.STARSHIPS -> painterResource(R.drawable.connecting_airports)
        Category.VEHICLES  -> painterResource(R.drawable.commute)
    }
}

@Composable
fun RelatedBox(id: String,
               name: String?,
               category: Category,
               modifier: Modifier = Modifier,
               width: Dp,
               navController: NavHostController) {
    val descriptionName = name ?: "\uD83D\uDE4A"
    Box(modifier = Modifier
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = modifier
                .width(width)
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                .clickable {
                    navigateToProperlyCompose(navController, id, category)
                })
        {
            Column(modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .background(brush = Brush.linearGradient(RELATED_BOXES_COLORS), alpha = 0.8f)) {
                AsyncImage(model = findImage(id, category),
                        error = choosePainter(category),
                        contentDescription = stringResource(R.string.movies),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                                .padding(EXTRA_SMALL_PADDING)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                                .background(Color.Magenta))
                Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = descriptionName,
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun InfoBox(
        cornerName: String,
        name: Any?,
        id: String? = null,
        category: Category? = null,
        width: Dp,
        navController: NavHostController? = null
) {
    val descriptionName = name ?: "\uD83D\uDE4A"
    val shouldBeClickable = id != null && category != null && navController != null
    val brushColors = if (shouldBeClickable) CLICKABLE_BOXES_COLORS else BOXES_COLORS
    Box(modifier = Modifier
            .width(width)
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = shouldBeClickable) {
                    navigateToProperlyCompose(navController!!, id!!, category!!)
                }
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
        ) {
            Column(modifier = Modifier.fillMaxSize().align(Alignment.Center)
                    .background(brush = Brush.linearGradient(
                            brushColors
                    ), alpha = 0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = cornerName,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                                .fillMaxWidth()
                                .padding(SMALL_PADDING_FOR_INFOBOX))
                Text(text = descriptionName.toString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = if (name != null) 8.dp else 0.dp),
                        fontSize = if (name != null) TextUnit.Unspecified else 36.sp,
                        fontWeight = if (shouldBeClickable) FontWeight.ExtraBold else FontWeight.Normal,
                        textDecoration = if (shouldBeClickable) TextDecoration.Underline else TextDecoration.None)
            }
        }
    }
}


@Composable
fun InfoBoxColumn(cornerName: String,
                  name: String?,
                  strings: List<String?>?,
                  width: Dp) {

    var descriptionName = name ?: strings ?: "U+1FAE2"
    if (descriptionName is List<*>) {
        strings?.joinToString(separator = "\n")?.also { descriptionName = it }
    }
    val scroll = rememberScrollState(0)
    Box(modifier = Modifier
            .width(width)
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
        ) {
            Column(modifier = Modifier.fillMaxSize().align(Alignment.Center)
                    .background(brush = Brush.linearGradient(BOXES_COLORS), alpha = 0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = cornerName,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                                .fillMaxWidth()
                                .padding(SMALL_PADDING_FOR_INFOBOX))
                Text(text = descriptionName as String,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = SMALL_PADDING_FOR_INFOBOX)
                                .verticalScroll(scroll))
            }
        }
    }
}

fun shouldInstanceLazyRow(firstObject: Any?,
                          secondObject: Any?,
                          thirdObject: Any?): Boolean {
    return (firstObject != null) && (secondObject != null) && secondObject != 0 && thirdObject != null
}

fun findImage(id: String, category: Category): String {
    return calculatePathToImage(category, id)
}

fun <T> findImage(item: T, category: Category): String {
    return when (category) {
        Category.FILMS     -> calculatePathToImage(category, (item as GetAllFilmsQuery.Film).id)
        Category.PEOPLE    -> calculatePathToImage(category, (item as GetAllPeoplesQuery.Person).id)
        Category.PLANETS   -> calculatePathToImage(category, (item as GetAllPlanetsQuery.Planet).id)
        Category.SPECIES   -> calculatePathToImage(category, (item as GetAllSpeciesQuery.Species).id)
        Category.STARSHIPS -> calculatePathToImage(category, (item as GetAllStarshipsQuery.Starship).id)
        Category.VEHICLES  -> calculatePathToImage(category, (item as GetAllVehiclesQuery.Vehicle).id)
    }
}

@Composable
fun clickableArrangementInLazyRow(
        count: Int?) = if (count != 1) Arrangement.SpaceBetween else Arrangement.Center