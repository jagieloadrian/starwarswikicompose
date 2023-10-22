package com.anjo.starwarswikicompose.services.usecases.vehicle

import com.anjo.GetVehicleQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetVehicleUseCase@Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(id:String): GetVehicleQuery.Vehicle? {
        return dataFetcher.fetchOneVehicle(id)
    }
}