package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import jakarta.inject.Inject

class GetImagesForObjectUseCase @Inject constructor(
        private val imageSliderRepository: ImageSliderRepository,
) {
    operator fun invoke(objectId: String, category: Category): List<ImageSliderModel> {
        return imageSliderRepository.getImagesForObjectFromRoom(objectId, category)
    }
}