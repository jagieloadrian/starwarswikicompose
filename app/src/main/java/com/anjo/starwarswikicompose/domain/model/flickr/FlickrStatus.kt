package com.anjo.starwarswikicompose.domain.model.flickr

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.error
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.fail
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.loading
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus.ok

enum class FlickrStatus {
    error,
    fail,
    ok,
    loading
}

fun FlickrStatus.isLoading(): Boolean {
    return this == loading
}

fun FlickrStatus.isFail(): Boolean {
    return this == fail
}

fun FlickrStatus.isOk(): Boolean {
    return this == ok
}

fun FlickrStatus.isError(): Boolean {
    return this == error
}

