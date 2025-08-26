package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertVehicleUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(vehicleDto: VehicleDto) = repository.insertVehicle(vehicleDto)
}