package com.anjo.starwarswikicompose.services.data.database.image

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.utils.Constants.IMAGE_SLIDER_TABLE

@Dao
interface ImageSliderDao {
    @Query("SELECT * FROM $IMAGE_SLIDER_TABLE WHERE objectId = :objectId and objectType = :category")
    fun getImagesForObjectFromRoom(objectId: String, category: Category): List<ImageSliderModel>

    @Insert(onConflict = REPLACE)
    suspend fun addImageToRoom(imageSliderModel: ImageSliderModel)

    @Delete
    suspend fun deleteImageFromRoom(imageSliderModel: ImageSliderModel)
}