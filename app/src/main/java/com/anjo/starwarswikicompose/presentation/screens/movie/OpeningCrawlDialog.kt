package com.anjo.starwarswikicompose.presentation.screens.movie

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalHeight
import com.anjo.starwarswikicompose.ui.theme.BOXES_COLORS
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING_FOR_INFOBOX
import com.anjo.starwarswikicompose.utils.Constants.EMOJI
import com.anjo.starwarswikicompose.utils.Constants.LESS_WHITE_BACKGROUND_COPY
import com.anjo.starwarswikicompose.utils.Constants.MAX_LINES_NUMBER
import com.anjo.starwarswikicompose.utils.TestTags.INFO_BOX_DIALOG_TAG
import com.anjo.starwarswikicompose.utils.TestTags.OPENING_CRAWL_DIALOG_TAG

@Composable
fun InfoBoxDialog(
        cornerName: String,
        description: String?,
        width: Dp,
) {
    val openDialog = remember { mutableStateOf(false) }
    val descriptionNotNull = description ?: EMOJI

    if (openDialog.value) {
        DialogWithOpeningCrawl(description = descriptionNotNull,
                cornerName = cornerName,
                onDismissAction = { openDialog.value = false })
    }
    Box(modifier = Modifier
            .width(width)
            .padding(EXTRA_SMALL_PADDING)
            .clickable {
                openDialog.value = true
            }
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(EXTRA_SMALL_PADDING))
            .testTag(INFO_BOX_DIALOG_TAG)) {
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
                                .padding(SMALL_PADDING_FOR_INFOBOX),
                        color = MaterialTheme.colorScheme.onSecondary)
                Text(text = descriptionNotNull,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = MAX_LINES_NUMBER,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = SMALL_PADDING_FOR_INFOBOX),
                        color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}

@Composable
fun DialogWithOpeningCrawl(
        description: String,
        cornerName: String,
        onDismissAction: () -> Unit,
) {
    val scroll = rememberScrollState(0)
    val height = ((getLocalHeight() / 3) * 2).dp

    Dialog(onDismissRequest = onDismissAction) {
        Card(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .padding(16.dp)
                        .testTag(OPENING_CRAWL_DIALOG_TAG),
                shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier
                    .fillMaxSize()
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
                                    .padding(SMALL_PADDING_FOR_INFOBOX),
                            color = MaterialTheme.colorScheme.onSecondary)
                    Text(text = description,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(8f)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = SMALL_PADDING_FOR_INFOBOX)
                                    .verticalScroll(scroll),
                            color = MaterialTheme.colorScheme.onSecondary)
                    Row(
                            modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = EXTRA_SMALL_PADDING)
                                    .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                                onClick = onDismissAction,
                                modifier = Modifier
                                        .alpha(0.9f),
                                shape = RoundedCornerShape(SMALL_PADDING),
                                colors = ButtonDefaults.buttonColors(Color.White.copy(LESS_WHITE_BACKGROUND_COPY))
                        ) {
                            Text(text = stringResource(R.string.close_text),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSecondary)
                        }

                    }
                }
            }
        }
    }

}