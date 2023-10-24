package com.anjo.starwarswikicompose.services.data.repository.image

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.utils.Category
import kotlinx.coroutines.flow.Flow

interface ImageSliderRepository {

    fun getImagesForObjectFromRoom(objectId:String, category:Category): Flow<List<ImageSliderModel>>

    suspend fun addImageToRoom(imageSliderModel: ImageSliderModel)

    suspend fun deleteImageFromRoom(imageSliderModel: ImageSliderModel)
}