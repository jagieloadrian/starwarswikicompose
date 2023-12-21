package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

import com.anjo.starwarswikicompose.domain.model.imageslider.ImageSliderModel
import com.anjo.starwarswikicompose.services.data.repository.image.ImageSliderRepository
import javax.inject.Inject

class DeleteImageFromRoomUseCase
@Inject constructor(
        private val imageSliderRepository: ImageSliderRepository,
) {
    suspend operator fun invoke(imageSliderModel: ImageSliderModel) {
        imageSliderRepository.deleteImageFromRoom(imageSliderModel)
    }
}