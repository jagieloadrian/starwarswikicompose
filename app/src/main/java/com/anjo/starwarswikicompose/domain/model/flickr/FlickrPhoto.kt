package com.anjo.starwarswikicompose.domain.model.flickr

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class FlickrPhoto(
        val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Int,
        val title: String,
        @JsonNames("ispublic")
        val isPublic: Int,
        @JsonNames("isfriend")
        val isFriend: Int,
        @JsonNames("isfamily")
        val isFamily: Int,
        val ownername: String,
)
