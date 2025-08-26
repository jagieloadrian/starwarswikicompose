package com.anjo.starwarswikicompose.services.intent

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.anjo.starwarswikicompose.utils.Constants.AUTHORITY_INTENT
import com.anjo.starwarswikicompose.utils.Constants.CACHE_NAME
import com.anjo.starwarswikicompose.utils.Constants.INTENT_SHARE_TITLE
import com.anjo.starwarswikicompose.utils.Constants.SHARE_ADDITIONAL_MESSAGE
import com.anjo.starwarswikicompose.utils.Constants.TEMP_FILE_NAME
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("QueryPermissionsNeeded")
//TODO handle query permission
suspend fun sendIntent(photoUrl: String, context: Context) {
    val bitmap = downloadImageFromUrl(context, photoUrl) ?: return
    saveInInternalStorage(context, bitmap)
    val contentUri = getUri(context)

    if (contentUri != null) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_TEXT, SHARE_ADDITIONAL_MESSAGE)
            putExtra(Intent.EXTRA_STREAM, contentUri)
            type = "image/jpg"
        }
        val shareIntent = Intent.createChooser(sendIntent, INTENT_SHARE_TITLE)
        val resInfoList: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY)

        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, contentUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(shareIntent)
    }
}

private fun getUri(context: Context): Uri? {
    val imagePath = File(context.cacheDir, CACHE_NAME)
    val newFile = File(imagePath, TEMP_FILE_NAME)
    return FileProvider.getUriForFile(context, AUTHORITY_INTENT, newFile)
}

private fun saveInInternalStorage(context: Context, bitmap: Bitmap) {
    try {
        val cachePath = File(context.cacheDir, CACHE_NAME)
        cachePath.mkdirs()
        val stream = FileOutputStream("$cachePath/$TEMP_FILE_NAME")
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    } catch (e: IOException) {
        Log.e("SHARE_INTENT", e.toString())
    }
}


private suspend fun downloadImageFromUrl(context: Context, photoUrl: String): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
            .data(photoUrl)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()
    return loader.execute(request).image?.toBitmap()
}