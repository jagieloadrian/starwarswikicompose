package com.anjo.starwarswikicompose.services.usecases.operationusecase.starship

import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetStarshipUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): Starship? {
        return dataFetcher.fetchOneStarship(id)
    }
}