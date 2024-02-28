package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): Person? {
        return dataFetcher.fetchOnePerson(id)
    }
}