package com.anjo.starwarswikicompose.services.data.repository.image

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.utils.Category

interface ImageSliderRepository {

    fun getImagesForObjectFromRoom(objectId:String, category:Category): List<ImageSliderModel>

    suspend fun addImageToRoom(imageSliderModel: ImageSliderModel)

    suspend fun deleteImageFromRoom(imageSliderModel: ImageSliderModel)
}