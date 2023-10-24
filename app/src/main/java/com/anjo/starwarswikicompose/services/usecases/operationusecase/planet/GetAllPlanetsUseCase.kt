package com.anjo.starwarswikicompose.services.usecases.operationusecase.planet

import com.anjo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllPlanetsUseCase @Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(): List<GetAllPlanetsQuery.Planet?>? {
        return dataFetcher.fetchPlanets()
    }
}