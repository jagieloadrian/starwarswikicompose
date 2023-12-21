package com.anjo.starwarswikicompose.services.data.repository

import com.anjo.starwarswikicompose.domain.model.flickr.FlickrPhotos
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrResponse
import com.anjo.starwarswikicompose.domain.model.flickr.FlickrStatus
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.anjo.starwarswikicompose.services.apollofetcher.DataFetcher
import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperations
import com.anjo.starwarswikicompose.services.imagefetcher.FlickrApi
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class OperationRepositoryTest {
    @RelaxedMockK
    lateinit var dataStoreOperations: DataStoreOperations

    @RelaxedMockK
    lateinit var dataFetcher: DataFetcher

    @RelaxedMockK
    lateinit var flickrApi: FlickrApi

    @InjectMockKs
    lateinit var operationRepository: OperationRepository


    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveOnboardingState then verify call`(boolean: Boolean) = runBlocking {
        //given
        val slot = slot<Boolean>()

        coEvery { dataStoreOperations.saveOnBoardingState(capture(slot)) } returns Unit
        //when
        operationRepository.saveOnboardingState(boolean)

        //then
        slot.captured shouldBe boolean
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when readOnboardingState then verify call`(boolean: Boolean) = runBlocking {
        //given
        val expected = flow { emit(boolean) }

        coEvery { dataStoreOperations.readingBoardingState() } returns expected
        //when
        val actual = operationRepository.readOnboardingState()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of people when fetchPeople then return list of people`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk(id = "fistId", name = "firstName", desc = "firstDesc"),
                UniversalChunk(id = "secondId", name = "secondName", desc = "secondDesc"),
                UniversalChunk(id = "thirdId", name = "thirdName", desc = "thirdDesc"))
        coEvery { dataFetcher.fetchPeoples() } returns expected

        //when
        val actual = operationRepository.fetchPeoples()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of specie when fetchSpecies then return list of specie`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk(id = "fistId", name = "firstName", desc = "firstDesc"),
                UniversalChunk(id = "secondId", name = "secondName", desc = "secondDesc"),
                UniversalChunk(id = "thirdId", name = "thirdName", desc = "thirdDesc"))
        coEvery { dataFetcher.fetchSpecies() } returns expected

        //when
        val actual = operationRepository.fetchSpecies()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of planet when fetchPlanets then return list of planet`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk(id = "fistId", name = "firstName", desc = "firstDesc"),
                UniversalChunk(id = "secondId", name = "secondName", desc = "secondDesc"),
                UniversalChunk(id = "thirdId", name = "thirdName", desc = "thirdDesc"))
        coEvery { dataFetcher.fetchPlanets() } returns expected

        //when
        val actual = operationRepository.fetchPlanets()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of starships when fetchStarships then return list of starships`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk(id = "fistId", name = "firstName", desc = "firstDesc"),
                UniversalChunk(id = "secondId", name = "secondName", desc = "secondDesc"),
                UniversalChunk(id = "thirdId", name = "thirdName", desc = "thirdDesc"))
        coEvery { dataFetcher.fetchStarships() } returns expected

        //when
        val actual = operationRepository.fetchStarships()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of vehicles when fetchVehicles then return list of vehicles`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk(id = "fistId", name = "firstName", desc = "firstDesc"),
                UniversalChunk(id = "secondId", name = "secondName", desc = "secondDesc"),
                UniversalChunk(id = "thirdId", name = "thirdName", desc = "thirdDesc"))
        coEvery { dataFetcher.fetchVehicles() } returns expected

        //when
        val actual = operationRepository.fetchVehicles()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given list of films when fetchFilms then return list of films`(): Unit = runBlocking {
        //given
        val expected = listOf(UniversalChunk(id = "fistId", name = "firstName", desc = "firstDesc"),
                UniversalChunk(id = "secondId", name = "secondName", desc = "secondDesc"),
                UniversalChunk(id = "thirdId", name = "thirdName", desc = "thirdDesc"))
        coEvery { dataFetcher.fetchFilms() } returns expected

        //when
        val actual = operationRepository.fetchFilms()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given film when fetchOneFilm then return film`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Movie(id = objectId, title = "title")
        coEvery { dataFetcher.fetchOneFilm(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneFilm(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given person when fetchOnePerson then return person`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Person(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOnePerson(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOnePerson(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given planet when fetchOnePlanet then return planet`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Planet(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOnePlanet(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOnePlanet(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given specie when fetchOneSpecie then return specie`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Specie(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOneSpecie(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneSpecie(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given starship when fetchOneStarship then return starship`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Starship(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOneStarship(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneStarship(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given film vehicle when fetchOneVehicle then return vehicle`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Vehicle(id = objectId, name = "title")
        coEvery { dataFetcher.fetchOneVehicle(objectId) } returns expected

        //when
        val actual = operationRepository.fetchOneVehicle(objectId)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given searchText when getSearchPhotosInfo then return flickrResponse`() = runBlocking {
        //given
        val expected = FlickrResponse(stat = FlickrStatus.ok, code = 200, photos = FlickrPhotos(0,0,0,0, emptyList()))
        val searchText = "searchText"

        coEvery { flickrApi.getSearchPhotosInfo(searchText = searchText) } returns expected

        //when
        val actual = operationRepository.getSearchPhotosInfo(searchText)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given mock  when getRecentPhotos then return flickrResponse`() = runBlocking {
        //given
        val expected = FlickrResponse(stat = FlickrStatus.ok, code = 200, photos = FlickrPhotos(0,0,0,0, emptyList()))

        coEvery { flickrApi.getRecentPhotos() } returns expected

        //when
        val actual = operationRepository.getRecentPhotos()

        //then
        actual shouldBe expected
    }
}