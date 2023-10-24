package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.GetSpecieQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetSpecieUseCase@Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(id:String): GetSpecieQuery.Species? {
        return dataFetcher.fetchOneSpecie(id)
    }
}