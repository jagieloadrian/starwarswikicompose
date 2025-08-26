@file:OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.services.data.repository

import com.anjo.starwarswikicompose.domain.dto.ConnectionDto
import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcher
import com.anjo.starwarswikicompose.services.data.repository.swmodels.SwModelRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ExtendWith(MockKExtension::class)
class OperationRepositoryTest {

    @RelaxedMockK
    lateinit var dataFetcher: DataFetcher

    @RelaxedMockK
    lateinit var swModelRepository: SwModelRepository

    @InjectMockKs
    lateinit var operationRepository: OperationRepository


    @Test
    fun `given list of people when fetchPeople then return list of people`(): Unit = runTest {
        //given
        val expected = listOf(
                UniversalChunkDto(id = "fistId", name = "firstName", desc = "firstDesc", category = PEOPLE),
                UniversalChunkDto(id = "secondId", name = "secondName", desc = "secondDesc", category = PEOPLE),
                UniversalChunkDto(id = "thirdId", name = "thirdName", desc = "thirdDesc", category = PEOPLE))
        coEvery { dataFetcher.fetchPeoples() } returns expected

        //when
        val actual = operationRepository.fetchPeoples()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of specie when fetchSpecies then return list of specie`(): Unit = runTest {
        //given
        val expected = listOf(
                UniversalChunkDto(id = "fistId", name = "firstName", desc = "firstDesc", category = SPECIES),
                UniversalChunkDto(id = "secondId", name = "secondName", desc = "secondDesc", category = SPECIES),
                UniversalChunkDto(id = "thirdId", name = "thirdName", desc = "thirdDesc", category = SPECIES))
        coEvery { dataFetcher.fetchSpecies() } returns expected

        //when
        val actual = operationRepository.fetchSpecies()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of planet when fetchPlanets then return list of planet`(): Unit = runTest {
        //given
        val expected = listOf(
                UniversalChunkDto(id = "fistId", name = "firstName", desc = "firstDesc", category = PLANETS),
                UniversalChunkDto(id = "secondId", name = "secondName", desc = "secondDesc", category = PLANETS),
                UniversalChunkDto(id = "thirdId", name = "thirdName", desc = "thirdDesc", category = PLANETS))
        coEvery { dataFetcher.fetchPlanets() } returns expected

        //when
        val actual = operationRepository.fetchPlanets()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of starships when fetchStarships then return list of starships`(): Unit = runTest {
        //given
        val expected = listOf(
                UniversalChunkDto(id = "fistId", name = "firstName", desc = "firstDesc", category = STARSHIPS),
                UniversalChunkDto(id = "secondId", name = "secondName", desc = "secondDesc", category = STARSHIPS),
                UniversalChunkDto(id = "thirdId", name = "thirdName", desc = "thirdDesc", category = STARSHIPS))
        coEvery { dataFetcher.fetchStarships() } returns expected

        //when
        val actual = operationRepository.fetchStarships()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of vehicles when fetchVehicles then return list of vehicles`(): Unit = runTest {
        //given
        val expected = listOf(
                UniversalChunkDto(id = "fistId", name = "firstName", desc = "firstDesc", category = VEHICLES),
                UniversalChunkDto(id = "secondId", name = "secondName", desc = "secondDesc", category = VEHICLES),
                UniversalChunkDto(id = "thirdId", name = "thirdName", desc = "thirdDesc", category = VEHICLES))
        coEvery { dataFetcher.fetchVehicles() } returns expected

        //when
        val actual = operationRepository.fetchVehicles()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of films when fetchFilms then return list of films`(): Unit = runTest {
        //given
        val expected = listOf(
                UniversalChunkDto(id = "fistId", name = "firstName", desc = "firstDesc", category = FILMS),
                UniversalChunkDto(id = "secondId", name = "secondName", desc = "secondDesc", category = FILMS),
                UniversalChunkDto(id = "thirdId", name = "thirdName", desc = "thirdDesc", category = FILMS))
        coEvery { dataFetcher.fetchFilms() } returns expected

        //when
        val actual = operationRepository.fetchFilms()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given film when fetchOneFilm then return film`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = MovieDto(id = objectId, title = "title")

        coEvery { dataFetcher.fetchOneFilm(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneFilm(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given person when fetchOnePerson then return person`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = PersonDto(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOnePerson(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOnePerson(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given planet when fetchOnePlanet then return planet`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = PlanetDto(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOnePlanet(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOnePlanet(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given specie when fetchOneSpecie then return specie`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = SpecieDto(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOneSpecie(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneSpecie(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given starship when fetchOneStarship then return starship`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = StarshipDto(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOneStarship(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneStarship(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given vehicle when fetchOneVehicle then return vehicle`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = VehicleDto(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOneVehicle(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneVehicle(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given personDto when insertPerson then verify calls`() = runTest {
        //given
        val personDto = PersonDto(id = "TODO()",
                name = "Adam",
                homeworld = UniversalChunkDto("", "Homeworld", "homeworld", PLANETS),
                specie = UniversalChunkDto("", "Specie", "specie", SPECIES),
                birthYear = "35BY",
                height = "1.85",
                mass = "85",
                gender = "X",
                hair = "hair",
                skin = "skin",
                movieConnection = ConnectionDto(2,
                        listOf(UniversalChunkDto("", "Movie1", "movie1", FILMS),
                                UniversalChunkDto("", "Movie2", "movie2", FILMS))),
                starshipConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Star1", "star1", STARSHIPS))),
                vehicleConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Veh1", "veh1", VEHICLES),
                )),
                isFromLocalStore = false)

        coEvery { swModelRepository.insertChunkCrossRef(any()) } returns Unit
        coEvery { swModelRepository.insertPerson(any()) } returns Uuid.random().toString()

        //when
        operationRepository.insertPerson(personDto)
        advanceUntilIdle()

        //then
        coVerify(exactly = 4) { swModelRepository.insertChunkCrossRef(any()) }
        coVerify(exactly = 1) { swModelRepository.insertPerson(any()) }
    }

    @Test
    fun `given movieDto when insertMovie then verify calls`() = runTest {
        //given
        val movieDto = MovieDto(id = "TODO()",
                title = "Adam",
                episodeId = "1",
                openingCrawl = "long text",
                producers = listOf("Producer1", "Producer2"),
                director = "director",
                releaseDate = "1970",
                characterConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Char1", "char1", PEOPLE))),
                planetConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Planet2", "planet2", PLANETS))),
                starshipConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Star1", "star1", STARSHIPS))),
                vehicleConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Veh1", "veh1", VEHICLES))),
                specieConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Specie2", "specie2", SPECIES))),
                isFromLocalStore = false)

        coEvery { swModelRepository.insertChunkCrossRef(any()) } returns Unit
        coEvery { swModelRepository.insertPerson(any()) } returns Uuid.random().toString()

        //when
        operationRepository.insertMovie(movieDto)
        advanceUntilIdle()

        //then
        coVerify(exactly = 5) { swModelRepository.insertChunkCrossRef(any()) }
        coVerify(exactly = 1) { swModelRepository.insertMovie(any()) }
    }

    @Test
    fun `given specieDto when insertSpecie then verify calls`() = runTest {
        //given
        val specieDto = SpecieDto(id = "TODO()",
                name = "name",
                language = "language",
                homeworld = UniversalChunkDto("", "Planet2", "planet2", PLANETS),
                classification = "classification",
                designation = "designation",
                averageHeight = "averageHeight",
                averageLifespan = "averageLifespan",
                eyeColors = listOf("blue", "green"),
                skinColors = listOf("blue", "green"),
                hairColors = listOf("blue", "green"),
                characterConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Char1", "char1", PEOPLE))),
                movieConnection = ConnectionDto(2,
                        listOf(UniversalChunkDto("", "Movie1", "movie1", FILMS),
                                UniversalChunkDto("", "Movie2", "movie2", FILMS))),
                isFromLocalStore = false)

        coEvery { swModelRepository.insertChunkCrossRef(any()) } returns Unit
        coEvery { swModelRepository.insertSpecie(any()) } returns Uuid.random().toString()

        //when
        operationRepository.insertSpecie(specieDto)
        advanceUntilIdle()

        //then
        coVerify(exactly = 3) { swModelRepository.insertChunkCrossRef(any()) }
        coVerify(exactly = 1) { swModelRepository.insertSpecie(any()) }
    }

    @Test
    fun `given vehicleDto when insertVehicle then verify calls`() = runTest {
        //given
        val vehicleDto = VehicleDto(id = "TODO()",
                name = "name",
                model = "model",
                vehicleClass = "vehicleClass",
                manufacturers = listOf("manu", "auto"),
                cost = "expensive",
                length = "long",
                crew = "many",
                passengers = "also_many",
                vMax = "power",
                cargoCapacity = "huge",
                consumables = "huge",
                characterConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Char1", "char1", PEOPLE))),
                movieConnection = ConnectionDto(2,
                        listOf(UniversalChunkDto("", "Movie1", "movie1", FILMS),
                                UniversalChunkDto("", "Movie2", "movie2", FILMS))),
                isFromLocalStore = false)

        coEvery { swModelRepository.insertChunkCrossRef(any()) } returns Unit
        coEvery { swModelRepository.insertVehicle(any()) } returns Uuid.random().toString()

        //when
        operationRepository.insertVehicle(vehicleDto)
        advanceUntilIdle()

        //then
        coVerify(exactly = 3) { swModelRepository.insertChunkCrossRef(any()) }
        coVerify(exactly = 1) { swModelRepository.insertVehicle(any()) }
    }

    @Test
    fun `given starshipDto when insertStarship then verify calls`() = runTest {
        //given
        val starshipDto = StarshipDto(id = "TODO()",
                name = "name",
                model = "model",
                starshipClass = "vehicleClass",
                manufacturers = listOf("manu", "auto"),
                cost = "expensive",
                length = "long",
                crew = "many",
                passengers = "also_many",
                vMax = "power",
                hyperdriveRating = "fast",
                megalight = "megalight",
                cargoCapacity = "huge",
                consumables = "huge",
                characterConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto("", "Char1", "char1", PEOPLE))),
                movieConnection = ConnectionDto(2,
                        listOf(UniversalChunkDto("", "Movie1", "movie1", FILMS),
                                UniversalChunkDto("", "Movie2", "movie2", FILMS))),
                isFromLocalStore = false)

        coEvery { swModelRepository.insertChunkCrossRef(any()) } returns Unit
        coEvery { swModelRepository.insertStarship(any()) } returns Uuid.random().toString()

        //when
        operationRepository.insertStarship(starshipDto)
        advanceUntilIdle()

        //then
        coVerify(exactly = 3) { swModelRepository.insertChunkCrossRef(any()) }
        coVerify(exactly = 1) { swModelRepository.insertStarship(any()) }
    }

    @Test
    fun `given planetDto when insertPlanet then verify calls`() = runTest {
        //given
        val planetDto = PlanetDto(id = "objectId1",
                name = "title",
                diameter = "1",
                gravity = "height",
                population = "it was",
                rotationPeriod = "gender",
                orbitalPeriod = "hairs",
                climates = listOf("skins", "skulls"), surfaceWater = "water",
                terrains = listOf("ground", "stones"),
                characterConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto(id = "charId", name = "charName", category = PEOPLE))),
                movieConnection = ConnectionDto(1, listOf(
                        UniversalChunkDto(id = "movieId", name = "movieName", category = FILMS))),
                isFromLocalStore = false)

        coEvery { swModelRepository.insertChunkCrossRef(any()) } returns Unit
        coEvery { swModelRepository.insertPlanet(any()) } returns Uuid.random().toString()

        //when
        operationRepository.insertPlanet(planetDto)
        advanceUntilIdle()

        //then
        coVerify(exactly = 2) { swModelRepository.insertChunkCrossRef(any()) }
        coVerify(exactly = 1) { swModelRepository.insertPlanet(any()) }
    }
}