package com.anjo.starwarswikicompose.services.imagefetcher

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_KEY
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_METHOD_RECENT_PHOTOS
import com.anjo.starwarswikicompose.utils.Constants.FLICKR_METHOD_SEARCH_PHOTOS
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/")
    suspend fun getSearchPhotosInfo(
            @Query("method") methodName: String = FLICKR_METHOD_SEARCH_PHOTOS,
            @Query("api_key") apiKey: String = FLICKR_KEY,
            @Query("safe_search") safeSearch: Int = 1,
            @Query("text") searchText:String,
            @Query("per_page") perPage: Int = 50,
            @Query("format") format:String = "json",
            @Query("extras") extras:String = "owner_name",
            @Query("nojsoncallback") notJson:Int = 1
    ) : FlickrResponse

    @GET("services/rest/")
    suspend fun getRecentPhotos(
            @Query("method") methodName: String = FLICKR_METHOD_RECENT_PHOTOS,
            @Query("api_key") apiKey: String = FLICKR_KEY,
            @Query("per_page") perPage: Int = 30,
            @Query("format") format:String = "json",
            @Query("extras") extras:String = "owner_name",
            @Query("nojsoncallback") notJson:Int = 1
    ) : FlickrResponse
}