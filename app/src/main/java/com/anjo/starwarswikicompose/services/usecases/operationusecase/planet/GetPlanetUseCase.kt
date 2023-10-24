package com.anjo.starwarswikicompose.services.usecases.operationusecase.planet

import com.anjo.GetPlanetQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetPlanetUseCase@Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(id:String): GetPlanetQuery.Planet? {
        return dataFetcher.fetchOnePlanet(id)
    }
}