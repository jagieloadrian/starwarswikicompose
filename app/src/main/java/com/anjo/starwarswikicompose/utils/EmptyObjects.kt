package com.anjo.starwarswikicompose.utils

import com.anjo.GetAllFilmsQuery
import com.anjo.GetAllPeoplesQuery
import com.anjo.GetAllPlanetsQuery
import com.anjo.GetAllSpeciesQuery
import com.anjo.GetAllStarshipsQuery
import com.anjo.GetAllVehiclesQuery

fun createAllPeopleEmptyObject(): GetAllPeoplesQuery.AllPeople {
    return GetAllPeoplesQuery.AllPeople(
            totalCount = 0,
            edges = emptyList(),
            pageInfo = GetAllPeoplesQuery.PageInfo(startCursor = "", hasNextPage = false, hasPreviousPage = false,
                    endCursor = ""),
            people = emptyList()
    )
}

fun createAllFilmsEmptyObject(): GetAllFilmsQuery.AllFilms {
    return GetAllFilmsQuery.AllFilms(
            totalCount = 0,
            edges = emptyList(),
            pageInfo = GetAllFilmsQuery.PageInfo(startCursor = "", hasNextPage = false, hasPreviousPage = false,
                    endCursor = ""),
            films = emptyList()
    )
}

fun createAllPlanetsEmptyObject(): GetAllPlanetsQuery.AllPlanets {
    return GetAllPlanetsQuery.AllPlanets(
            totalCount = 0,
            edges = emptyList(),
            pageInfo = GetAllPlanetsQuery.PageInfo(startCursor = "", hasNextPage = false, hasPreviousPage = false,
                    endCursor = ""),
            planets = emptyList()
    )
}

fun createAllSpeciesEmptyObject(): GetAllSpeciesQuery.AllSpecies {
    return GetAllSpeciesQuery.AllSpecies(
            totalCount = 0,
            edges = emptyList(),
            pageInfo = GetAllSpeciesQuery.PageInfo(startCursor = "", hasNextPage = false, hasPreviousPage = false,
                    endCursor = ""),
            species = emptyList()
    )
}

fun createAllVehiclesEmptyObject(): GetAllVehiclesQuery.AllVehicles {
    return GetAllVehiclesQuery.AllVehicles(
            totalCount = 0,
            edges = emptyList(),
            pageInfo = GetAllVehiclesQuery.PageInfo(startCursor = "", hasNextPage = false, hasPreviousPage = false,
                    endCursor = ""),
            vehicles = emptyList()
    )
}

fun createAllStarshipsEmptyObject(): GetAllStarshipsQuery.AllStarships {
    return GetAllStarshipsQuery.AllStarships(
            totalCount = 0,
            edges = emptyList(),
            pageInfo = GetAllStarshipsQuery.PageInfo(startCursor = "", hasNextPage = false, hasPreviousPage = false,
                    endCursor = ""),
            starships = emptyList()
    )
}