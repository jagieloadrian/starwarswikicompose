package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllSpeciesUseCase @Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(): List<GetAllSpeciesQuery.Species?>? {
        return dataFetcher.fetchSpecies()
    }
}