package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetSpecieUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): Specie? {
        return dataFetcher.fetchOneSpecie(id)
    }
}