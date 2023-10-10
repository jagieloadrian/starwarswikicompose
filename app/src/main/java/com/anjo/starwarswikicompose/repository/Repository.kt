package com.anjo.starwarswikicompose.repository

import com.anjo.starwarswikicompose.services.FilmFetcher
import javax.inject.Inject

class Repository @Inject constructor(
    private val filmService:FilmFetcher
) {

    fun getFilms() = filmService.fetchFilms()
}