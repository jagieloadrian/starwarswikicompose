package com.anjo.starwarswikicompose.services.usecases.operationusecase.person

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class GetAllPeopleUseCase @Inject constructor(
        private val operationRepository: OperationRepository,
) {
    suspend operator fun invoke(): List<UniversalChunkDto> {
        return operationRepository.fetchPeoples()
    }
}