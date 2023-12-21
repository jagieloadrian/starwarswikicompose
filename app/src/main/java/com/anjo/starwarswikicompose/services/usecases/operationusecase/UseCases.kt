package com.anjo.starwarswikicompose.services.usecases.operationusecase

import com.anjo.starwarswikicompose.services.usecases.operationusecase.images.GetRecentImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.images.GetSearchImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.movie.GetAllFilmsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.movie.GetMovieUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.onboarding.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.person.GetAllPeopleUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.person.GetPersonUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.planet.GetAllPlanetsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.planet.GetPlanetUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.specie.GetAllSpeciesUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.specie.GetSpecieUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.starship.GetAllStarshipsUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.starship.GetStarshipUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle.GetAllVehicleUseCase
import com.anjo.starwarswikicompose.services.usecases.operationusecase.vehicle.GetVehicleUseCase

data class UseCases(
        val saveOnboardingUseCase: SaveOnboardingUseCase,
        val readOnboardingUseCase: ReadOnboardingUseCase,
        val getAllFilmsUseCase: GetAllFilmsUseCase,
        val getMovieUseCase: GetMovieUseCase,
        val getAllPeopleUseCase: GetAllPeopleUseCase,
        val getPersonUseCase: GetPersonUseCase,
        val getAllPlanetsUseCase: GetAllPlanetsUseCase,
        val getPlanetUseCase: GetPlanetUseCase,
        val getAllSpeciesUseCase: GetAllSpeciesUseCase,
        val getSpecieUseCase: GetSpecieUseCase,
        val getAllStarshipsUseCase: GetAllStarshipsUseCase,
        val getStarshipUseCase: GetStarshipUseCase,
        val getAllVehicleUseCase: GetAllVehicleUseCase,
        val getVehicleUseCase: GetVehicleUseCase,
        val getRecentImagesUseCase: GetRecentImagesUseCase,
        val getSearchImagesUseCase: GetSearchImagesUseCase,
)