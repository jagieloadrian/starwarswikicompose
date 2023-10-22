package com.anjo.starwarswikicompose.services.usecases.planet

import com.anjo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetAllPlanetsUseCase @Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(): List<GetAllPlanetsQuery.Planet?>? {
        return dataFetcher.fetchPlanets()
    }
}