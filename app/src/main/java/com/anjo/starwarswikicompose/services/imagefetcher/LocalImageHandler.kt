package com.anjo.starwarswikicompose.services.imagefetcher

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.utils.Constants.ASSETS_PATH
import java.io.ByteArrayOutputStream
import java.io.File

fun findImageAsset(id: String, category: Category, isFromLocalStorage: Boolean, context: Context): String {
    return if (isFromLocalStorage) {
        "${context.filesDir.absolutePath}/$id.jpg"
    } else {
        calculatePathToImage(category, id)
    }
}

fun saveImage(context: Context, image: Bitmap, newFileName: String) {
    val byteArray = image.toByteArray()
    saveImageToInternalStorage(context.filesDir, byteArray, newFileName)
    Log.i("LOCAL_IMAGE_HANDLER", "saveImage: $newFileName.jpg")
}

private fun calculatePathToImage(category: Category, id: String): String {
    return "$ASSETS_PATH/${category.categoryName.lowercase()}/$id.jpg"
}

private fun saveImageToInternalStorage(filesDir: File, imageData: ByteArray, fileName: String): File {
    val file = File(filesDir, "$fileName.jpg")
    file.outputStream().use { output ->
        output.write(imageData)
    }
    return file
}

private fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}