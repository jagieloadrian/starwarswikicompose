package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.mapper.toModel
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertChunkUseCase @Inject constructor(
        private val operationRepository: OperationRepository
) {
    suspend operator fun invoke(chunkDto: UniversalChunkDto) = operationRepository.insertChunk(chunkDto.toModel())
}
