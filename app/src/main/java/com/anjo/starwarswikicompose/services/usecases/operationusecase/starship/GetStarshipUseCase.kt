package com.anjo.starwarswikicompose.services.usecases.operationusecase.starship

import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetStarshipUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): StarshipDto? {
        return dataFetcher.fetchOneStarship(id)
    }
}