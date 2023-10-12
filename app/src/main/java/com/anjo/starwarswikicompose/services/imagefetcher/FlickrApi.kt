package com.anjo.starwarswikicompose.services.imagefetcher

import com.anjo.starwarswikicompose.domain.model.FlickrResponse
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_KEY
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_METHOD_SEARCH_PHOTOS
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET
    suspend fun getSearchPhotosInfo(
            @Query("method") methodName: String = FLICKR_METHOD_SEARCH_PHOTOS,
            @Query("api_key") apiKey: String = FLICKR_KEY,
            @Query("text") searchText:String,
            @Query("format") format:String = "json"
    ) : FlickrResponse
}