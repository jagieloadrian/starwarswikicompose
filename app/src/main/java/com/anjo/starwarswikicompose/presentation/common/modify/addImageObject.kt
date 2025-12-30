package com.anjo.starwarswikicompose.presentation.common.modify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.services.imagefetcher.findImageAsset
import com.anjo.starwarswikicompose.ui.theme.EXTRA_SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.VEHICLE_PICTURE_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.ADD_IMAGE_OBJECT_TAG
import com.anjo.starwarswikicompose.utils.isFromLocalStorage
import java.io.File
import java.io.InputStream

@Composable
fun addImageObject(context: Context, chunkDto: UniversalChunkDto? = null): Bitmap? {
    var selectedBitmap by remember { mutableStateOf(getBitmapFromAsset(context, chunkDto)) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            if (isJPG(context, uri)) {
                val inputStream = context.contentResolver.openInputStream(it)
                selectedBitmap = BitmapFactory.decodeStream(inputStream)
            }
        }
    }

    Box(modifier = Modifier
            .size(VEHICLE_PICTURE_HEIGHT)
            .clip(RoundedCornerShape(MEDIUM_PADDING))
            .border(EXTRA_SMALL_PADDING,
                    MaterialTheme.colorScheme.onSecondary,
                    RoundedCornerShape(MEDIUM_PADDING))
            .background(MaterialTheme.colorScheme.primary.copy(0.2f))
            .clickable(onClick = {
                launcher.launch("image/*")
            })
            .testTag(ADD_IMAGE_OBJECT_TAG),
            contentAlignment = Alignment.Center) {
        selectedBitmap?.let { bitmap ->
            Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(VEHICLE_PICTURE_HEIGHT)
            )
        } ?: Icon(Icons.Filled.Add, contentDescription = "AddNewObjectField",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                        .size(TOP_APP_BAR_HEIGHT)
                        .clip(RoundedCornerShape(MEDIUM_PADDING))
                        .background(MaterialTheme.colorScheme.onSecondary.copy(0.4f)))
    }
    return selectedBitmap
}

fun isJPG(context: Context, uri: Uri): Boolean {
    val mime = context.contentResolver.getType(uri)
    return mime == "image/jpeg"
}

fun getBitmapFromAsset(context: Context, chunkDto: UniversalChunkDto?): Bitmap? {
    return chunkDto?.let {
        val isFromLocal = isFromLocalStorage(chunkDto.sourceType)
        val uriPath = findImageAsset(chunkDto.id, chunkDto.category,
                isFromLocal, context)
        try {
            val input = if (isFromLocal) getInputStreamFromFile(uriPath) else getInputStreamFromAsset(context, uriPath)
            val bitmap = BitmapFactory.decodeStream(input)
            input.close()
            bitmap
        } catch (e: Exception) {
            Log.e(ADD_IMAGE_OBJECT_TAG, "getBitmapFromAsset: null", e)
            null
        }
    }
}

private fun getInputStreamFromAsset(context: Context, uriPath: String): InputStream {
    return context.assets.open(uriPath.split("/").takeLast(2).joinToString("/"))
}

private fun getInputStreamFromFile(uriPath: String): InputStream {
    return File(uriPath).inputStream()
}
