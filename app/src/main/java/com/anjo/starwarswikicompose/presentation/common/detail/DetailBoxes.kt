package com.anjo.starwarswikicompose.presentation.common.detail

import android.annotation.SuppressLint
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import coil3.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.UnitName
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.navigation.navigateToProperlyCompose
import com.anjo.starwarswikicompose.services.imagefetcher.findImageAsset
import com.anjo.starwarswikicompose.ui.theme.BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.CLICKABLE_BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.INFO_BOX_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.RELATED_BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING_FOR_INFOBOX
import com.anjo.starwarswikicompose.utils.Constants.EMOJI
import com.anjo.starwarswikicompose.utils.Constants.UNKNOWN
import com.anjo.starwarswikicompose.utils.TestTags.RELATED_BUTTON_TAG
import com.anjo.starwarswikicompose.utils.getDescriptionName
import com.anjo.starwarswikicompose.utils.isFromLocalStorage

@Composable
fun choosePainter(category: Category): Painter {
    return when (category) {
        Category.ALL       -> painterResource(R.drawable.baseline_all_inclusive_24)
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
        connection: ConnectionDto, halfWidth: Dp,
        navController: NavHostController,
) {
    val count = connection.totalCount
    if (shouldInstanceLazyRow(count)) {
        LazyRow(modifier = Modifier
                .height(INFO_BOX_HEIGHT)
                .fillMaxWidth(),
                horizontalArrangement = clickableArrangementInLazyRow(count)) {
            items(items = connection.objects) { item ->
                RelatedBox(item, width = halfWidth,
                        navController = navController)
            }
        }
    }
}

@Composable
fun InfoBox(
        cornerName: String,
        unitName: UnitName? = null,
        name: String?,
        width: Dp,
) {
    val descriptionName = getDescriptionName(name, unitName)
    Box(modifier = Modifier
            .width(width)
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
        ) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(brush = Brush.linearGradient(BOXES_COLORS), alpha = 0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = cornerName,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(SMALL_PADDING_FOR_INFOBOX)
                                .basicMarquee(),
                        color = MaterialTheme.colorScheme.onSecondary)
                Text(text = descriptionName,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = if (name != null) 8.dp else 0.dp)
                                .basicMarquee(),
                        fontSize = if (name != null) TextUnit.Unspecified else 36.sp,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.None,
                        color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}

@Composable
fun InfoBox(
        cornerName: String,
        item: UniversalChunkDto,
        width: Dp,
        navController: NavHostController
) {
    val descriptionName = getDescriptionName(item.name, null)
    val shouldBeClickable = item.id.isNotBlank()
    val brushColors = if (shouldBeClickable) CLICKABLE_BOXES_COLORS else BOXES_COLORS
    Box(modifier = Modifier
            .width(width)
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = shouldBeClickable) {
                    navigateToProperlyCompose(navController, item)
                }
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
        ) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(brush = Brush.linearGradient(brushColors), alpha = 0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = cornerName,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(SMALL_PADDING_FOR_INFOBOX)
                                .basicMarquee(),
                        color = MaterialTheme.colorScheme.onSecondary)
                Text(text = descriptionName,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 8.dp)
                                .basicMarquee(),
                        fontSize = TextUnit.Unspecified,
                        fontWeight = FontWeight.ExtraBold,
                        textDecoration = if (shouldBeClickable) TextDecoration.Underline else TextDecoration.None,
                        color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}

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
        Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
        ) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(brush = Brush.linearGradient(BOXES_COLORS), alpha = 0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = cornerName,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(SMALL_PADDING_FOR_INFOBOX)
                                .basicMarquee(),
                        color = MaterialTheme.colorScheme.onSecondary)
                Text(text = descriptionName as String,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = SMALL_PADDING_FOR_INFOBOX)
                                .verticalScroll(scroll),
                        color = MaterialTheme.colorScheme.onSecondary)
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
        item: UniversalChunkDto,
        modifier: Modifier = Modifier,
        width: Dp,
        navController: NavHostController,
) {
    Box(modifier = Modifier
            .padding(EXTRA_SMALL_PADDING)
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))) {
        Box(modifier = modifier
                .width(width)
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                .clickable {
                    navigateToProperlyCompose(navController, item)
                }
                .testTag(RELATED_BUTTON_TAG))
        {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(brush = Brush.linearGradient(RELATED_BOXES_COLORS), alpha = 0.8f)) {
                AsyncImage(model = findImageAsset(item.id, item.category, isFromLocalStorage(item.sourceType),
                        LocalContext.current),
                        error = choosePainter(item.category),
                        contentDescription = "RelatedBox ${item.name}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                                .weight(1f)
                                .padding(EXTRA_SMALL_PADDING)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                                .background(Color.Transparent))
                Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondary
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
    Row(modifier = Modifier
            .height(INFO_BOX_HEIGHT)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
        InfoBox(
                firstCornerName,
                firstUnitName,
                firstValue,
                width = halfWidth)
        InfoBox(
                secondCornerName,
                secondUnitName,
                secondValue,
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
    Row(modifier = Modifier
            .height(INFO_BOX_HEIGHT)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
        InfoBox(
                firstCornerName,
                firstUnitName,
                firstValue,
                width = thirdWidth)
        InfoBox(secondCornerName,
                secondUnitName,
                secondValue,
                width = thirdWidth)
        InfoBox(
                thirdCornerName,
                thirdUnitName,
                thirdValue,
                width = thirdWidth)
    }
}

fun shouldInstanceLazyRow(count: Int): Boolean {
    return count > 0
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun getLocalWidth(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun getLocalHeight(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}