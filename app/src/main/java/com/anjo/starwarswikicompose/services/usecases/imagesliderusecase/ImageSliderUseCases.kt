package com.anjo.starwarswikicompose.services.usecases.imagesliderusecase

data class ImageSliderUseCases(
        val getImagesForObjectUseCase: GetImagesForObjectUseCase,
        val addImageToRoomUseCase: AddImageToRoomUseCase,
        val deleteImageFromRoomUseCase: DeleteImageFromRoomUseCase
)