package com.anjo.starwarswikicompose.services.usecases.images

import com.anjo.starwarswikicompose.data.Repository
import com.anjo.starwarswikicompose.domain.model.FlickrResponse
import javax.inject.Inject

class GetRecentImagesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): FlickrResponse {
        return repository.getRecentPhotos()
    }
}