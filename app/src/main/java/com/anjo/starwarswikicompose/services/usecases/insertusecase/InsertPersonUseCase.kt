package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertPersonUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(person: PersonDto) = repository.insertPerson(person)
}