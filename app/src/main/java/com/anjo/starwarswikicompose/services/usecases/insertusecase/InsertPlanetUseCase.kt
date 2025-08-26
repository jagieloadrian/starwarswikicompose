package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertPlanetUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(planet: PlanetDto) = repository.insertPlanet(planet)
}