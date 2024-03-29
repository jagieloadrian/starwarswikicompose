package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.ui.theme.BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.CLICKABLE_BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.RELATED_BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING_FOR_INFOBOX
import com.anjo.starwarswikicompose.utils.Constants.EMOJI
import com.anjo.starwarswikicompose.utils.Constants.RELATED_BUTTON_TAG
import com.anjo.starwarswikicompose.utils.Constants.UNKNOWN
import com.anjo.starwarswikicompose.utils.calculatePathToImage
import com.anjo.starwarswikicompose.utils.getDescriptionName
import com.anjo.starwarswikicompose.utils.navigateToProperlyCompose

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
fun ShowHorizontalBoxes(
        connection: Connection, category: Category, halfWidth: Dp,
        navController: NavHostController,
) {
    val count = connection.totalCount
    if (shouldInstanceLazyRow(count)) {
        LazyRow(modifier = Modifier.height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = connection.objects) { item ->
                RelatedBox(item.id, item.name, category, width = halfWidth, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfoBox(
        cornerName: String,
        name: String?,
        unitName: UnitName? = null,
        id: String? = null,
        category: Category? = null,
        width: Dp,
        navController: NavHostController? = null,
) {
    val descriptionName = getDescriptionName(name, unitName)
    val shouldBeClickable = !(id.isNullOrBlank()) && category != null && navController != null
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
                                .padding(SMALL_PADDING_FOR_INFOBOX)
                                .basicMarquee())
                Text(text = descriptionName,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = if (name != null) 8.dp else 0.dp)
                                .basicMarquee(),
                        fontSize = if (name != null) TextUnit.Unspecified else 36.sp,
                        fontWeight = if (shouldBeClickable) FontWeight.ExtraBold else FontWeight.Normal,
                        textDecoration = if (shouldBeClickable) TextDecoration.Underline else TextDecoration.None)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfoBoxColumn(
        cornerName: String,
        name: String?,
        strings: List<String?>?,
        width: Dp,
) {

    var descriptionName = getNameOrNull(name) ?: strings ?: EMOJI
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
                                .padding(SMALL_PADDING_FOR_INFOBOX)
                                .basicMarquee())
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

private fun getNameOrNull(name: String?): String? {
    return if (name.isNullOrBlank() || name.equals(UNKNOWN, true)) null else name
}

@Composable
fun clickableArrangementInLazyRow(count: Int) = if (count != 1) Arrangement.SpaceBetween else Arrangement.Center

@Composable
private fun RelatedBox(
        id: String,
        name: String?,
        category: Category,
        modifier: Modifier = Modifier,
        width: Dp,
        navController: NavHostController,
) {
    val descriptionName = if (name.isNullOrEmpty()) EMOJI else name
    Box(modifier = Modifier
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = modifier
                .width(width)
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                .clickable {
                    navigateToProperlyCompose(navController, id, category)
                }.testTag(RELATED_BUTTON_TAG))
        {
            Column(modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .background(brush = Brush.linearGradient(RELATED_BOXES_COLORS), alpha = 0.8f)) {
                AsyncImage(model = findImage(id, category),
                        error = choosePainter(category),
                        contentDescription = "RelatedBox $descriptionName",
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
fun DoubleInfoBox(
        firstCornerName: String,
        firstValue: String,
        firstUnitName: UnitName?,
        secondCornerName: String,
        secondValue: String,
        secondUnitName: UnitName?,
        halfWidth: Dp,
) {
    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
        InfoBox(
                firstCornerName,
                firstValue,
                firstUnitName,
                width = halfWidth)
        InfoBox(
                secondCornerName,
                secondValue,
                secondUnitName,
                width = halfWidth)
    }
}

@Composable
fun TripleInfoBox(
        firstCornerName: String,
        firstValue: String,
        firstUnitName: UnitName?,
        secondCornerName: String,
        secondValue: String,
        secondUnitName: UnitName?,
        thirdCornerName: String,
        thirdValue: String,
        thirdUnitName: UnitName?,
        thirdWidth: Dp,
) {
    Row(modifier = Modifier.height(INFO_BOX_HEIGHT)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
        InfoBox(
                firstCornerName,
                firstValue,
                firstUnitName,
                width = thirdWidth)
        InfoBox(secondCornerName,
                secondValue,
                secondUnitName,
                width = thirdWidth)
        InfoBox(
                thirdCornerName,
                thirdValue,
                thirdUnitName,
                width = thirdWidth)
    }
}

fun shouldInstanceLazyRow(count: Int): Boolean {
    return count > 0
}

fun findImage(id: String, category: Category): String {
    return calculatePathToImage(category, id)
}