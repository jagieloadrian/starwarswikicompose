package com.anjo.starwarswikicompose.services.usecases.specie

import com.anjo.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.data.Repository
import javax.inject.Inject

class GetAllSpeciesUseCase @Inject constructor(
        private val dataFetcher: Repository
) {
    suspend operator fun invoke(): List<GetAllSpeciesQuery.Species?>? {
        return dataFetcher.fetchSpecies()
    }
}