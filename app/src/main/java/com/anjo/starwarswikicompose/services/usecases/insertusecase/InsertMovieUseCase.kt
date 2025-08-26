package com.anjo.starwarswikicompose.services.usecases.insertusecase

import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import jakarta.inject.Inject

class InsertMovieUseCase @Inject constructor(
        private val repository: OperationRepository
) {
    suspend operator fun invoke(movie: MovieDto) = repository.insertMovie(movie)
}