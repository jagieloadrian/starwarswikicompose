package com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle

import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetVehicleUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): VehicleDto? {
        return dataFetcher.fetchOneVehicle(id)
    }
}