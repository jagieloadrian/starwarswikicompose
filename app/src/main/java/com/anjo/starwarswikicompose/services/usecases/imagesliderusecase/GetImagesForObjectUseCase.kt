package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import com.anjo.starwarswikicompose.utils.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesForObjectUseCase @Inject constructor(
        private val imageSliderRepository: ImageSliderRepository
) {
    operator fun invoke(objectId:String, category: Category): Flow<List<ImageSliderModel>> {
        return imageSliderRepository.getImagesForObjectFromRoom(objectId, category)
    }
}