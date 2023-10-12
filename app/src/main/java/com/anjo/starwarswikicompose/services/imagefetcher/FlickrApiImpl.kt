package com.anjo.starwarswikicompose.services.imagefetcher

import com.anjo.starwarswikicompose.domain.model.FlickrResponse
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrClientBuilder.provideFlickrApi
import retrofit2.Retrofit

class FlickrApiImpl(private val retrofit: Retrofit) : FlickrApi {

    override suspend fun getSearchPhotosInfo(methodName: String, apiKey: String, searchText: String, format: String,
                                             notJson: Int): FlickrResponse {
        return provideFlickrApi(retrofit).getSearchPhotosInfo(apiKey = apiKey, searchText = searchText)
    }

    override suspend fun getRecentPhotos(methodName: String, apiKey: String, perPage: Int, format: String,
                                         notJson: Int): FlickrResponse {
        return provideFlickrApi(retrofit).getRecentPhotos()
    }
}