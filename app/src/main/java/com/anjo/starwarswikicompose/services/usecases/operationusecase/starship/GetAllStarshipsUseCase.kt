package com.anjo.starwarswikicompose.services.usecases.operationusecase.starship

import com.anjo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllStarshipsUseCase @Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(): List<GetAllStarshipsQuery.Starship?>? {
        return dataFetcher.fetchStarships()
    }
}