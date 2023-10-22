package com.anjo.starwarswikicompose.services.usecases.planet

import com.anjo.GetPlanetQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetPlanetUseCase@Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(id:String): GetPlanetQuery.Planet? {
        return dataFetcher.fetchOnePlanet(id)
    }
}