package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetAllSpeciesUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(): List<UniversalChunkDto> {
        return dataFetcher.fetchSpecies()
    }
}