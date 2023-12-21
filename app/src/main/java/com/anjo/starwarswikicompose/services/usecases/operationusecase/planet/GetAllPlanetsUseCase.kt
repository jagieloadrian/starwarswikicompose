package com.anjo.starwarswikicompose.services.usecases.operationusecase.planet

import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllPlanetsUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(): List<UniversalChunk> {
        return dataFetcher.fetchPlanets()
    }
}