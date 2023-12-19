package com.anjo.starwarswikicompose.services.usecases.operationusecase.movie

import com.anjo.starwarswikicompose.GetFilmQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(id:String): GetFilmQuery.Film? {
        return dataFetcher.fetchOneFilm(id)
    }
}