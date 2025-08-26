package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetSpecieUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): SpecieDto? {
        return dataFetcher.fetchOneSpecie(id)
    }
}