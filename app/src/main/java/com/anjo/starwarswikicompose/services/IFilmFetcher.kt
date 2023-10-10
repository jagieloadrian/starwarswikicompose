package com.anjo.starwarswikicompose.services

import com.anjo.GetAllFilmsQuery

interface IFilmFetcher {

    fun fetchFilms(): GetAllFilmsQuery.AllFilms?
}