package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertStarshipUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(starshipDto: StarshipDto) = repository.insertStarship(starshipDto)
}