package com.anjo.starwarswikicompose.domain.model.sw

enum class DetailObjectState {
    SUCCESS, ERROR, LOADING
}

fun DetailObjectState.isSuccess(): Boolean {
    return this == DetailObjectState.SUCCESS
}

fun DetailObjectState.isLoading(): Boolean {
    return this == DetailObjectState.LOADING
}

fun DetailObjectState.isError(): Boolean {
    return this == DetailObjectState.ERROR
}