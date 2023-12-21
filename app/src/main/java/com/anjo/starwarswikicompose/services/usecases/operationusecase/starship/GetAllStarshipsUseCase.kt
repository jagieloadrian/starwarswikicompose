package com.anjo.starwarswikicompose.services.usecases.operationusecase.starship

import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllStarshipsUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(): List<UniversalChunk> {
        return dataFetcher.fetchStarships()
    }
}