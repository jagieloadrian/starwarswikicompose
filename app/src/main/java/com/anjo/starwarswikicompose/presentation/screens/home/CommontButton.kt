package com.anjo.starwarswikicompose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.navigation.navigateToProperlyCompose
import com.anjo.starwarswikicompose.presentation.common.detail.choosePainter
import com.anjo.starwarswikicompose.services.imagefetcher.findImageAsset
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.ROUND_CORNER_BUTTONS
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.TestTags.COMMON_BUTTON_TAG
import com.anjo.starwarswikicompose.utils.TestTags.UNIVERSAL_COLUMN_TEXT_TAG
import com.anjo.starwarswikicompose.utils.TestTags.UNIVERSAL_ROW_TEXT_TAG
import com.anjo.starwarswikicompose.utils.getDescriptionName
import com.anjo.starwarswikicompose.utils.isFromLocalStorage

@Composable
fun CommonButton(navController: NavHostController, item: UniversalChunkDto) {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(ROUND_CORNER_BUTTONS))
                    .background(brush = Brush.linearGradient(listOf(
                            Color.Yellow, Color.Red, Color.Blue
                    )))
                    .clickable { navigateToProperlyCompose(navController, item) }
                    .testTag(COMMON_BUTTON_TAG),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = findImageAsset(item.id, item.category, isFromLocalStorage(item.sourceType),
                    LocalContext.current),
                    error = choosePainter(item.category),
                    contentDescription = stringResource(R.string.movies),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(EXTRA_SMALL_PADDING)
                            .align(alignment = Alignment.CenterVertically)
                            .clip(CircleShape)
                            .background(Color.Transparent))
            ColumnText(item, modifier = Modifier.weight(4f))
        }
    }
}

@Composable
private fun ColumnText(item: UniversalChunkDto, modifier: Modifier) {
    if (Category.FILMS == item.category) {
        return UniversalRowText(item, modifier)
    }
    return UniversalColumnText(item, modifier)
}

@Composable
fun UniversalRowText(item: UniversalChunkDto, modifier: Modifier) {
    Row(modifier = modifier
            .fillMaxSize()
            .testTag(UNIVERSAL_ROW_TEXT_TAG)) {
        Text(text = item.name,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName,
                color = MaterialTheme.colorScheme.onSecondary)
        Text(text = item.desc,
                modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName,
                color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun UniversalColumnText(item: UniversalChunkDto, modifier: Modifier) {
    val description = getDescriptionName(item.desc, null)
    Column(modifier = modifier
            .fillMaxSize()
            .testTag(UNIVERSAL_COLUMN_TEXT_TAG)) {
        Text(text = item.name,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondary)
        Text(text = description,
                color = MaterialTheme.colorScheme.onSecondary)
    }
}
