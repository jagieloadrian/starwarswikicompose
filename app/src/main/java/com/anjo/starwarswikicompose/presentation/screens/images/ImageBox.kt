package com.anjo.starwarswikicompose.presentation.screens.images

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhoto
import com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu.ContextImageModelView
import com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu.ImageItemMenu
import com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu.generateActions
import com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu.runContextAction
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
        navHostController: NavHostController,
) {
    val title = if (photo.title.isEmpty()) "\uD83D\uDE4A" else photo.title
    val authorName = if (photo.ownername.isEmpty()) "\uD83D\uDE4A" else photo.ownername
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    val photoUrl = buildImageUrl(photo)
    val context = LocalContext.current
    val contextImageModelView = hiltViewModel<ContextImageModelView>()
    val dropdownItems = generateActions(navHostController, photoUrl, contextImageModelView)

    if (isContextMenuVisible) {
        ImageItemMenu(isContextMenuVisible = isContextMenuVisible,
                pressOffset = pressOffset,
                onDismissRequest = {
                    isContextMenuVisible = false
                },
                itemHeight = itemHeight,
                onClick = {
                    runContextAction(it, context, contextImageModelView)
                    isContextMenuVisible = false
                },
                dropdownItems = dropdownItems)
    }

    Box(modifier = Modifier.fillMaxSize()
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .border(SMALL_BORDER, Color.Black, shape = RoundedCornerShape(SMALL_PADDING))) {
        Box(modifier = Modifier
                .fillMaxSize()
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                            onLongPress = {
                                isContextMenuVisible = true
                                pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                            },
                            onPress = {
                                val press = PressInteraction.Press(it)
                                interactionSource.emit(press)
                                tryAwaitRelease()
                                interactionSource.emit(PressInteraction.Release(press))
                            }
                    )
                }
                .clip(RoundedCornerShape(SMALL_PADDING))) {
            Column(modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(MEDIUM_PADDING)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                ShowImage(photoUrl = photoUrl,
                        modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally))
                InfoRow(stringResource(R.string.title_text), title)
                InfoRow(stringResource(R.string.author_text), authorName)
            }
        }
    }
}

@Composable
private fun ShowImage(
        photoUrl: String,
        modifier: Modifier = Modifier,
) {
    AsyncImage(model = photoUrl,
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
