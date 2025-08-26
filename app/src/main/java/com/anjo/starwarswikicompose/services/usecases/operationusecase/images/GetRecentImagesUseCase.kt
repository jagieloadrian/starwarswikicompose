package com.anjo.starwarswikicompose.services.usecases.operationusecase.images

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.services.data.repository.PhotoOperationRepository
import jakarta.inject.Inject

class GetRecentImagesUseCase @Inject constructor(private val operationRepository: PhotoOperationRepository) {
    suspend operator fun invoke(): FlickrResponse {
        return operationRepository.getRecentPhotos()
    }
}