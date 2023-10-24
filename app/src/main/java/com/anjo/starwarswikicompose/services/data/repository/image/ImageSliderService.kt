package com.anjo.starwarswikicompose.services.data.repository.image

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.data.database.ImageSliderDao
import com.anjo.starwarswikicompose.utils.Category
import kotlinx.coroutines.flow.Flow

class ImageSliderService(
        private val imageSliderDao: ImageSliderDao
) :ImageSliderRepository {
    override fun getImagesForObjectFromRoom(objectId: String, category: Category): Flow<List<ImageSliderModel>> {
        return imageSliderDao.getImagesForObjectFromRoom(objectId, category)
    }

    override suspend fun addImageToRoom(imageSliderModel: ImageSliderModel) {
        return imageSliderDao.addImageToRoom(imageSliderModel)
    }

    override suspend fun deleteImageFromRoom(imageSliderModel: ImageSliderModel) {
        return imageSliderDao.deleteImageFromRoom(imageSliderModel)
    }
}