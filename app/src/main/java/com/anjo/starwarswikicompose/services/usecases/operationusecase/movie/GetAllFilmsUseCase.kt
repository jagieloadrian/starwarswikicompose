package com.anjo.starwarswikicompose.services.usecases.operationusecase.movie

import com.anjo.starwarswikicompose.GetAllFilmsQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllFilmsUseCase @Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(): List<GetAllFilmsQuery.Film?>? {
        return dataFetcher.fetchFilms()
    }
}