package com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetAllVehicleUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(): List<UniversalChunkDto> {
        return dataFetcher.fetchVehicles()
    }
}