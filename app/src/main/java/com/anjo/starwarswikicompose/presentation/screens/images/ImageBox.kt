package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.FlickrPhoto
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_SPACING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.MAX_LINES_NUMBER
import com.anjo.starwarswikicompose.utils.Constants.MEDIUM_WHITE_BACKGROUND_COPY
import com.anjo.starwarswikicompose.utils.buildImageUrl

@Composable
fun ImageBox(
        photo: FlickrPhoto,
) {
    val title = if (photo.title.isEmpty()) "\uD83D\uDE4A" else photo.title
    val authorName = if (photo.ownername.isEmpty()) "\uD83D\uDE4A" else photo.ownername

    Box(modifier = Modifier.fillMaxSize()
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(SMALL_PADDING))) {
        Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(SMALL_PADDING))) {
            Column(modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(MEDIUM_PADDING)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = buildImageUrl(photo),
                        placeholder = painterResource(R.drawable.image_icon),
                        error = painterResource(R.drawable.ic_network_error),
                        contentDescription = stringResource(R.string.flickr_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                                .height(PICTURE_HEIGHT)
                                .background(Color.White.copy(MEDIUM_WHITE_BACKGROUND_COPY))
                                .align(alignment = Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
                )
                InfoRow("Title: ", title)
                InfoRow("Author name: ", authorName)
            }
        }
    }
}


@Composable
private fun InfoRow(fieldName: String, description: String) {
    Row(modifier = Modifier.fillMaxWidth()
            .background(color = Color.White.copy(MEDIUM_WHITE_BACKGROUND_COPY))
            .padding(all = PAGING_INDICATOR_SPACING),
            horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = fieldName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                maxLines = MAX_LINES_NUMBER,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
        )
        Text(text = description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                maxLines = MAX_LINES_NUMBER,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                        .fillMaxSize()
                        .weight(4f)
        )
    }
}
