package com.anjo.starwarswikicompose.services.usecases.movie

import com.anjo.GetFilmQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(id:String): GetFilmQuery.Film? {
        return dataFetcher.fetchOneFilm(id)
    }
}