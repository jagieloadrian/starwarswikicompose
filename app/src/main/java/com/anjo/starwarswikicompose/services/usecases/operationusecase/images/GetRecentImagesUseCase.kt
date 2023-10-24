package com.anjo.starwarswikicompose.services.usecases.operationusecase.images

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetRecentImagesUseCase @Inject constructor(private val operationRepository: OperationRepository) {
    suspend operator fun invoke(): FlickrResponse {
        return operationRepository.getRecentPhotos()
    }
}