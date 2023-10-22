package com.anjo.starwarswikicompose.services.usecases.images

import com.anjo.starwarswikicompose.data.Repository
import com.anjo.starwarswikicompose.domain.model.FlickrResponse
import javax.inject.Inject

class GetSearchImagesUseCase @Inject constructor(
        private val repository: Repository
) {
    suspend operator fun invoke(searchText: String): FlickrResponse {
        return repository.getSearchPhotosInfo(searchText)
    }

}