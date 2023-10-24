package com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle

import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllVehicleUseCase @Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(): List<GetAllVehiclesQuery.Vehicle?>? {
        return dataFetcher.fetchVehicles()
    }
}