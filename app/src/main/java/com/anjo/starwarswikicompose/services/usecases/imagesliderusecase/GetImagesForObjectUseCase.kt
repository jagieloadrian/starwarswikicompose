package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import com.anjo.starwarswikicompose.utils.Category
import javax.inject.Inject

class GetImagesForObjectUseCase @Inject constructor(
        private val imageSliderRepository: ImageSliderRepository
) {
    operator fun invoke(objectId:String, category: Category): List<ImageSliderModel> {
        return imageSliderRepository.getImagesForObjectFromRoom(objectId, category)
    }
}