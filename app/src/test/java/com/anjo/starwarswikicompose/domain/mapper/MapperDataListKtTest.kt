package com.anjo.starwarswikicompose.domain.mapper

import com.anjo.starwarswikicompose.apollo.GetAllFilmsQuery
import com.anjo.starwarswikicompose.apollo.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.apollo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.apollo.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.apollo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.apollo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
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
        val expected = listOf(UniversalChunkDto(id = "objectId1", name = "name1", desc = "1", FILMS),
                UniversalChunkDto(id = "objectId2", name = "", desc = "2", FILMS),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "null", FILMS))

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
        val expected = listOf(UniversalChunkDto(id = "objectId1", name = "name1", desc = "1", PEOPLE),
                UniversalChunkDto(id = "objectId2", name = "", desc = "2", PEOPLE),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "", PEOPLE))

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
        val expected = listOf(UniversalChunkDto(id = "objectId1", name = "name1", desc = "1 citizens", PLANETS),
                UniversalChunkDto(id = "objectId2", name = "", desc = "2 citizens", PLANETS),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "0 citizens", PLANETS))

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
        val expected = listOf(UniversalChunkDto(id = "objectId1", name = "name1", desc = "1", SPECIES),
                UniversalChunkDto(id = "objectId2", name = "", desc = "2", SPECIES),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "", SPECIES))

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
        val expected = listOf(UniversalChunkDto(id = "objectId1", name = "name1", desc = "1", VEHICLES),
                UniversalChunkDto(id = "objectId2", name = "", desc = "2", VEHICLES),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "", VEHICLES))

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
        val expected = listOf(UniversalChunkDto(id = "objectId1", name = "name1", desc = "1", STARSHIPS),
                UniversalChunkDto(id = "objectId2", name = "", desc = "2", STARSHIPS),
                UniversalChunkDto(id = "objectId3", name = "name3", desc = "", STARSHIPS))

        //when
        val actual = starship.mapFromStarships()

        //then
        actual shouldBe expected
    }
}