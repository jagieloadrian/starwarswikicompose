package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import javax.inject.Inject

class GetAllPeopleUseCase @Inject constructor(
        private val operationRepository: OperationRepository
) {
    suspend operator fun invoke(): List<GetAllPeoplesQuery.Person?>? {
        return operationRepository.fetchPeoples()
    }
}