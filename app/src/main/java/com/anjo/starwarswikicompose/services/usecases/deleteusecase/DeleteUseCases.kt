package com.anjo.starwarswikicompose.services.usecases.deleteusecase

data class DeleteUseCases(
        val deleteMovieUseCase: DeleteMovieUseCase,
        val deletePersonUseCase: DeletePersonUseCase,
        val deleteStarshipUseCase: DeleteStarshipUseCase,
        val deleteVehicleUseCase: DeleteVehicleUseCase,
        val deleteSpecieUseCase: DeleteSpecieUseCase,
        val deletePlanetUseCase: DeletePlanetUseCase,
)