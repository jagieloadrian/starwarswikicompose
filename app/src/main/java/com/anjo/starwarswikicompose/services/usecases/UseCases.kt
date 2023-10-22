package com.anjo.starwarswikicompose.services.usecases

import com.anjo.starwarswikicompose.services.usecases.images.GetRecentImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.images.GetSearchImagesUseCase
import com.anjo.starwarswikicompose.services.usecases.movie.GetAllFilmsUseCase
import com.anjo.starwarswikicompose.services.usecases.movie.GetMovieUseCase
import com.anjo.starwarswikicompose.services.usecases.onboarding.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.onboarding.SaveOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.person.GetAllPeopleUseCase
import com.anjo.starwarswikicompose.services.usecases.person.GetPersonUseCase
import com.anjo.starwarswikicompose.services.usecases.planet.GetAllPlanetsUseCase
import com.anjo.starwarswikicompose.services.usecases.planet.GetPlanetUseCase
import com.anjo.starwarswikicompose.services.usecases.specie.GetAllSpeciesUseCase
import com.anjo.starwarswikicompose.services.usecases.specie.GetSpecieUseCase
import com.anjo.starwarswikicompose.services.usecases.starship.GetAllStarshipsUseCase
import com.anjo.starwarswikicompose.services.usecases.starship.GetStarshipUseCase
import com.anjo.starwarswikicompose.services.usecases.vehicle.GetAllVehicleUseCase
import com.anjo.starwarswikicompose.services.usecases.vehicle.GetVehicleUseCase

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
        val getSearchImagesUseCase: GetSearchImagesUseCase
)