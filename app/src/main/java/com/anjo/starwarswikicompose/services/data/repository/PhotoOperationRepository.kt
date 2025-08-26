package com.anjo.starwarswikicompose.services.data.repository

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrApi
import jakarta.inject.Inject

class PhotoOperationRepository @Inject constructor(
        private val flickrApi: FlickrApi,
) {

    suspend fun getSearchPhotosInfo(searchText: String): FlickrResponse {
        return flickrApi.getSearchPhotosInfo(searchText = searchText)
    }

    suspend fun getRecentPhotos(): FlickrResponse {
        return flickrApi.getRecentPhotos()
    }

}