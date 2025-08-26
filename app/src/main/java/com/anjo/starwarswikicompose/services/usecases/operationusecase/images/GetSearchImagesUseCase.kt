package com.anjo.starwarswikicompose.services.usecases.operationusecase.images

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.services.data.repository.PhotoOperationRepository
import jakarta.inject.Inject

class GetSearchImagesUseCase @Inject constructor(private val operationRepository: PhotoOperationRepository) {
    suspend operator fun invoke(searchText: String): FlickrResponse {
        return operationRepository.getSearchPhotosInfo(searchText)
    }

}