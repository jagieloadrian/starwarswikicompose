package com.anjo.starwarswikicompose.services.usecases.movie

import com.anjo.GetAllFilmsQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetAllFilmsUseCase @Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(): List<GetAllFilmsQuery.Film?>? {
        return dataFetcher.fetchFilms()
    }
}