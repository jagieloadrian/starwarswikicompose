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
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.presentation.common.choosePainter
import com.anjo.starwarswikicompose.presentation.common.findImage
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.utils.navigateToProperlyCompose

@Composable
fun CommonButton(navController: NavHostController, item: UniversalChunk, category: Category) {
    Box(
            modifier = Modifier.fillMaxSize()
                    .clip(shape = RoundedCornerShape(35.dp))
                    .background(brush = Brush.linearGradient(listOf(
                            Color.Yellow, Color.Red, Color.Blue
                    )))
                    .clickable { navigateToProperlyCompose(navController, item.id, category) },
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = findImage(item.id, category),
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
private fun ColumnText(item: UniversalChunk, category: Category, modifier: Modifier) {
    if (category == Category.FILMS) {
        return UniversalRowText(item, modifier)
    }
    return UniversalColumnText(item, modifier)
}

@Composable
fun UniversalRowText(item: UniversalChunk, modifier: Modifier) {
    Row(modifier = modifier.fillMaxSize()) {
        Text(text = item.name,
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName)
        Text(text = item.desc,
                modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontFamily = SOLOFontName)
    }
}

@Composable
fun UniversalColumnText(item: UniversalChunk, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = item.name,
                fontWeight = FontWeight.ExtraBold)
        Text(text = item.desc)
    }
}
