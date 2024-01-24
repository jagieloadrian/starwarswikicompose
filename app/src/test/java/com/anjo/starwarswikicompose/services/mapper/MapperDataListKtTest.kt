package com.anjo.starwarswikicompose.services.mapper

import com.anjo.starwarswikicompose.GetAllFilmsQuery
import com.anjo.starwarswikicompose.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import io.kotest.matchers.shouldBe
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MapperDataListKtTest {


    @Test
    fun `given film when mapFromFilms then return list of universalChunk`() {
        //given
        val film = listOf(
                GetAllFilmsQuery.Film(id = "objectId1", title = "name1", episodeID = 1),
                GetAllFilmsQuery.Film(id = "objectId2", title = null, episodeID = 2),
                GetAllFilmsQuery.Film(id = "objectId3", title = "name3", episodeID = null))
        val expected = listOf(UniversalChunk(id = "objectId1", name = "name1", desc = "1"),
                UniversalChunk(id = "objectId2", name = "", desc = "2"),
                UniversalChunk(id = "objectId3", name = "name3", desc = "null"))

        //when
        val actual = film.mapFromFilms()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given person list when mapFromPersons then return list of universalChunk`() {
        //given
        val people = listOf(
                GetAllPeoplesQuery.Person(id = "objectId1", name = "name1", birthYear = "1"),
                GetAllPeoplesQuery.Person(id = "objectId2", name = null, birthYear = "2"),
                GetAllPeoplesQuery.Person(id = "objectId3", name = "name3", birthYear = null))
        val expected = listOf(UniversalChunk(id = "objectId1", name = "name1", desc = "1"),
                UniversalChunk(id = "objectId2", name = "", desc = "2"),
                UniversalChunk(id = "objectId3", name = "name3", desc = ""))

        //when
        val actual = people.mapFromPersons()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given planet list when mapFromPlanets then return list of universalChunk`() {
        //given
        val planets = listOf(
                GetAllPlanetsQuery.Planet(id = "objectId1", name = "name1", population = 1.5),
                GetAllPlanetsQuery.Planet(id = "objectId2", name = null, population = 2.5),
                GetAllPlanetsQuery.Planet(id = "objectId3", name = "name3", population = null))
        val expected = listOf(UniversalChunk(id = "objectId1", name = "name1", desc = "1 citizens"),
                UniversalChunk(id = "objectId2", name = "", desc = "2 citizens"),
                UniversalChunk(id = "objectId3", name = "name3", desc = "0 citizens"))

        //when
        val actual = planets.mapFromPlanets()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given specie list when mapFromSpecies then return list of universalChunk`() {
        //given
        val specie = listOf(
                GetAllSpeciesQuery.Species(id = "objectId1", name = "name1", language = "1"),
                GetAllSpeciesQuery.Species(id = "objectId2", name = null, language = "2"),
                GetAllSpeciesQuery.Species(id = "objectId3", name = "name3", language = null))
        val expected = listOf(UniversalChunk(id = "objectId1", name = "name1", desc = "1"),
                UniversalChunk(id = "objectId2", name = "", desc = "2"),
                UniversalChunk(id = "objectId3", name = "name3", desc = ""))

        //when
        val actual = specie.mapFromSpecies()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given vehicle list when mapFromVehicles then return list of universalChunk`() {
        //given
        val vehicles = listOf(
                GetAllVehiclesQuery.Vehicle(id = "objectId1", name = "name1", model = "1"),
                GetAllVehiclesQuery.Vehicle(id = "objectId2", name = null, model = "2"),
                GetAllVehiclesQuery.Vehicle(id = "objectId3", name = "name3", model = null))
        val expected = listOf(UniversalChunk(id = "objectId1", name = "name1", desc = "1"),
                UniversalChunk(id = "objectId2", name = "", desc = "2"),
                UniversalChunk(id = "objectId3", name = "name3", desc = ""))

        //when
        val actual = vehicles.mapFromVehicles()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given starship list when mapFromStarships then return list of universalChunk`() {
        //given
        val starship = listOf(
                GetAllStarshipsQuery.Starship(id = "objectId1", name = "name1", model = "1"),
                GetAllStarshipsQuery.Starship(id = "objectId2", name = null, model = "2"),
                GetAllStarshipsQuery.Starship(id = "objectId3", name = "name3", model = null))
        val expected = listOf(UniversalChunk(id = "objectId1", name = "name1", desc = "1"),
                UniversalChunk(id = "objectId2", name = "", desc = "2"),
                UniversalChunk(id = "objectId3", name = "name3", desc = ""))

        //when
        val actual = starship.mapFromStarships()

        //then
        actual shouldBe expected
    }
}