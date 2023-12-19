package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.starwarswikicompose.GetPersonQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetPersonUseCase@Inject constructor(
        private val dataFetcher: OperationRepository
) {
    suspend operator fun invoke(id:String): GetPersonQuery.Person? {
        return dataFetcher.fetchOnePerson(id)
    }
}