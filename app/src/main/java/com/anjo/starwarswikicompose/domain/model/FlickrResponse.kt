package com.anjo.starwarswikicompose.domain.model

import com.anjo.starwarswikicompose.domain.model.FlickrStatus.fail
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
        val photos: FlickrPhotos?=null,
        val stat: FlickrStatus? = fail,
        val code : Int? = 0,
        val message: String? = ""
)
