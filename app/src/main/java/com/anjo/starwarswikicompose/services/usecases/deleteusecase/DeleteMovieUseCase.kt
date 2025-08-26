package com.anjo.starwarswikicompose.services.usecases.deleteusecase

import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class DeleteMovieUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(movie: String) = repository.removeMovie(movie)
}