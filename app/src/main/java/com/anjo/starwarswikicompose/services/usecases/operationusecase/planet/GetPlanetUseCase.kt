package com.anjo.starwarswikicompose.services.usecases.operationusecase.planet

import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetPlanetUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): PlanetDto? {
        return dataFetcher.fetchOnePlanet(id)
    }
}