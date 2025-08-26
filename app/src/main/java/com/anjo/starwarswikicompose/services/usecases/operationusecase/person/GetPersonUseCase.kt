package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetPersonUseCase @Inject constructor(
        private val dataFetcher: OperationRepository,
) {
    suspend operator fun invoke(id: String): PersonDto? {
        return dataFetcher.fetchOnePerson(id)
    }
}