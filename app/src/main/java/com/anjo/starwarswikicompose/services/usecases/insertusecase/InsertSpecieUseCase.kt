package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertSpecieUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(specieDto: SpecieDto) = repository.insertSpecie(specieDto)
}