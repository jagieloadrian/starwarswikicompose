package com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle

import com.anjo.starwarswikicompose.GetVehicleQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetVehicleUseCase@Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(id:String): GetVehicleQuery.Vehicle? {
        return dataFetcher.fetchOneVehicle(id)
    }
}