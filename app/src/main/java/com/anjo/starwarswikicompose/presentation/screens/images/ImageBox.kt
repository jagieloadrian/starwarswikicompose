package com.anjo.starwarswikicompose.presentation.screens.images

import android.content.ClipData
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.presentation.common.button.CornerButton
import com.anjo.starwarswikicompose.services.intent.sendIntent
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_SPACING
import com.anjo.starwarswikicompose.ui.theme.PICTURE_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.utils.Constants.CLIPBOARD_URI_KEY
import com.anjo.starwarswikicompose.utils.Constants.EMOJI
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_BASE_URL_IMAGE
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_EXT
import com.anjo.starwarswikicompose.utils.Constants.MAX_LINES_NUMBER
import com.anjo.starwarswikicompose.utils.Constants.MEDIUM_WHITE_BACKGROUND_COPY
import com.anjo.starwarswikicompose.utils.TestTags.FLICKR_IMAGE_BOX_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ImageBox(
        photo: FlickrPhoto,
        addCopyAction: () -> Unit,
) {
    val title = photo.title.ifEmpty { EMOJI }
    val authorName = photo.ownername.ifEmpty { EMOJI }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboard.current
    val photoUrl = buildImageUrl(photo)

    Box(modifier = Modifier
            .fillMaxSize()
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(SMALL_PADDING))
            .testTag(FLICKR_IMAGE_BOX_TAG)) {
        Box(modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(SMALL_PADDING))) {
            Column(modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(MEDIUM_PADDING)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                ShowImage(photoUrl = photoUrl,
                        modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally))
                InfoRow(stringResource(R.string.title_text), title)
                InfoRow(stringResource(R.string.author_text), authorName)
            }
            Surface(modifier = Modifier
                    .background(Color.Transparent)
                    .align(Alignment.TopEnd),
                    color = Color.Transparent) {
                CornerButton(imageVector = Icons.Filled.Share, "cornerButton") {
                    scope.launch(Dispatchers.IO) { sendIntent(photoUrl = photoUrl, context = context) }
                }

            }
            Surface(modifier = Modifier
                    .background(Color.Transparent)
                    .align(Alignment.TopStart),
                    color = Color.Transparent) {
                CornerButton(painter = painterResource(R.drawable.baseline_content_copy_24)) {
                    addCopyAction()
                    scope.launch(Dispatchers.IO) {
                        copyToClipBoard(clipboardManager = clipboardManager, text = photoUrl)
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowImage(
        photoUrl: String,
        modifier: Modifier = Modifier,
) {
    val headers = NetworkHeaders.Builder()
            .set("User-Agent", "Mozilla/5.0 (Android 11; Mobile; rv:109.0) Gecko/109.0 Firefox/115.0")
            .build()
    val request = ImageRequest.Builder(LocalContext.current)
            .data(photoUrl)
            .httpHeaders(headers)
            .crossfade(true)
            .build()
    AsyncImage(model = request,
            placeholder = painterResource(R.drawable.image_icon),
            error = painterResource(R.drawable.ic_network_error),
            contentDescription = stringResource(R.string.flickr_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
                    .height(PICTURE_HEIGHT)
                    .background(Color.White.copy(MEDIUM_WHITE_BACKGROUND_COPY))
                    .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))
    )
}

@Composable
private fun InfoRow(fieldName: String, description: String) {
    Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.tertiary.copy(MEDIUM_WHITE_BACKGROUND_COPY))
            .padding(all = PAGING_INDICATOR_SPACING),
            horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = fieldName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = MAX_LINES_NUMBER,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
        )
        Text(text = description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = MAX_LINES_NUMBER,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                        .fillMaxSize()
                        .weight(4f)
        )
    }
}

private suspend fun copyToClipBoard(clipboardManager: Clipboard, text: String) {
    val clipData = ClipData.newPlainText(CLIPBOARD_URI_KEY, text).toClipEntry()
    clipboardManager.setClipEntry(clipData)
}

private fun buildImageUrl(photo: FlickrPhoto): String {
    return "$FLICKR_BASE_URL_IMAGE/${photo.server}/${photo.id}_${photo.secret}$FLICKR_EXT"
}