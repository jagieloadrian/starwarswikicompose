package com.anjo.starwarswikicompose.services.usecases.vehicle

import com.anjo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetAllVehicleUseCase @Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(): List<GetAllVehiclesQuery.Vehicle?>? {
        return dataFetcher.fetchVehicles()
    }
}