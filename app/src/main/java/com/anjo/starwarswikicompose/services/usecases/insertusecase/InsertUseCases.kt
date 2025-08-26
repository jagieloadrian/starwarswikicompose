package com.anjo.starwarswikicompose.services.usecases.insertusecase

data class InsertUseCases(
        val insertChunkUseCase: InsertChunkUseCase,
        val insertMovieUseCase: InsertMovieUseCase,
        val insertPersonUseCase: InsertPersonUseCase,
        val insertStarshipUseCase: InsertStarshipUseCase,
        val insertVehicleUseCase: InsertVehicleUseCase,
        val insertSpecieUseCase: InsertSpecieUseCase,
        val insertPlanetUseCase: InsertPlanetUseCase,
)