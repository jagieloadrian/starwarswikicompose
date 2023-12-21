package com.anjo.starwarswikicompose.domain.model.flickr

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class FlickrPhotos(
        val page: Int,
        val pages: Int,
        @JsonNames("perpage")
        val perPage: Int,
        val total: Int,
        val photo: List<FlickrPhoto>,
)
