package com.anjo.starwarswikicompose.domain.model.flickr

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.error
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
        val photos: FlickrPhotos?=null,
        val stat: FlickrStatus = error,
        val code : Int? = 0,
        val message: String = ""
)
