package com.anjo.starwarswikicompose.services.apollofetcher

import android.util.Log
import com.anjo.starwarswikicompose.GetAllFilmsQuery
import com.anjo.starwarswikicompose.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.GetFilmQuery
import com.anjo.starwarswikicompose.GetPersonQuery
import com.anjo.starwarswikicompose.GetPlanetQuery
import com.anjo.starwarswikicompose.GetSpecieQuery
import com.anjo.starwarswikicompose.GetStarshipQuery
import com.anjo.starwarswikicompose.GetVehicleQuery
import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.domain.model.sw.Person
import com.anjo.starwarswikicompose.domain.model.sw.Planet
import com.anjo.starwarswikicompose.domain.model.sw.Specie
import com.anjo.starwarswikicompose.domain.model.sw.Starship
import com.anjo.starwarswikicompose.domain.model.sw.Vehicle
import com.anjo.starwarswikicompose.domain.model.sw.common.Connection
import com.anjo.starwarswikicompose.domain.model.sw.common.UniversalChunk
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class DataFetcherImplTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient

    @InjectMockKs
    lateinit var dataFetcherImpl: DataFetcherImpl

    @Test
    fun `given response GetAllFilmsQuery object when fetch films then return list of universalChunk`(): Unit =
        runBlocking {
            //given
            val film1 = GetAllFilmsQuery.Film(
                    title = "Film1",
                    episodeID = 1,
                    id = "movieId1"
            )
            val film2 = GetAllFilmsQuery.Film(
                    title = "Film2",
                    episodeID = 2,
                    id = "movieId2"
            )
            val allFilms = GetAllFilmsQuery.Data(
                    GetAllFilmsQuery.AllFilms(
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllFilmsQuery.PageInfo(film1.id, hasNextPage = false, hasPreviousPage = false,
                                    film2.id),
                            films = listOf(film1, film2, null)
                    ))
            val dataApolloResponse = ApolloResponse.Builder(
                    operation = GetAllFilmsQuery(),
                    UUID.randomUUID(),
                    data = allFilms
            ).build()

            val expected = listOf(UniversalChunk(film1.id, film1.title ?: "", film1.episodeID.toString()),
                    UniversalChunk(film2.id, film2.title ?: "", film2.episodeID.toString()))

            coEvery { apolloClient.query(GetAllFilmsQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchFilms()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetAllPeoplesQuery object when fetch films then return list of universalChunk`(): Unit =
        runBlocking {
            //given
            val object1 = GetAllPeoplesQuery.Person(
                    name = "Object1",
                    id = "id1",
                    birthYear = "12ABY"
            )
            val object2 = GetAllPeoplesQuery.Person(
                    name = "Object2",
                    id = "id2",
                    birthYear = "22ABY"
            )
            val allObjects = GetAllPeoplesQuery.Data(
                    GetAllPeoplesQuery.AllPeople(
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllPeoplesQuery.PageInfo(object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            people = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = ApolloResponse.Builder(
                    operation = GetAllPeoplesQuery(),
                    UUID.randomUUID(),
                    data = allObjects
            ).build()

            val expected = listOf(UniversalChunk(object1.id, object1.name ?: "", object1.birthYear.toString()),
                    UniversalChunk(object2.id, object2.name ?: "", object2.birthYear.toString()))

            coEvery { apolloClient.query(GetAllPeoplesQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchPeoples()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetAllPlanetsQuery object when fetch films then return list of universalChunk`(): Unit =
        runBlocking {
            //given
            val object1 = GetAllPlanetsQuery.Planet(
                    name = "Object1",
                    id = "id1",
                    population = 123.00
            )
            val object2 = GetAllPlanetsQuery.Planet(
                    name = "Object2",
                    id = "id2",
                    population = null
            )
            val allObjects = GetAllPlanetsQuery.Data(
                    GetAllPlanetsQuery.AllPlanets(
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllPlanetsQuery.PageInfo(object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            planets = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = ApolloResponse.Builder(
                    operation = GetAllPlanetsQuery(),
                    UUID.randomUUID(),
                    data = allObjects
            ).build()

            val expected = listOf(UniversalChunk(object1.id, object1.name ?: "", "123 citizens"),
                    UniversalChunk(object2.id, object2.name ?: "", "0 citizens"))

            coEvery { apolloClient.query(GetAllPlanetsQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchPlanets()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetAllSpeciesQuery object when fetch films then return list of universalChunk`(): Unit =
        runBlocking {
            //given
            val object1 = GetAllSpeciesQuery.Species(
                    name = "Object1",
                    id = "id1",
                    language = "language"
            )
            val object2 = GetAllSpeciesQuery.Species(
                    name = "Object2",
                    id = "id2",
                    language = "lang"
            )
            val allObjects = GetAllSpeciesQuery.Data(
                    GetAllSpeciesQuery.AllSpecies(
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllSpeciesQuery.PageInfo(object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            species = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = ApolloResponse.Builder(
                    operation = GetAllSpeciesQuery(),
                    UUID.randomUUID(),
                    data = allObjects
            ).build()

            val expected = listOf(UniversalChunk(object1.id, object1.name ?: "", object1.language ?: ""),
                    UniversalChunk(object2.id, object2.name ?: "", object2.language ?: ""))

            coEvery { apolloClient.query(GetAllSpeciesQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchSpecies()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetAllStarshipsQuery object when fetch films then return list of universalChunk`(): Unit =
        runBlocking {
            //given
            val object1 = GetAllStarshipsQuery.Starship(
                    name = "Object1",
                    id = "id1",
                    model = "model1"
            )
            val object2 = GetAllStarshipsQuery.Starship(
                    name = "Object2",
                    id = "id2",
                    model = "model2"
            )
            val allObjects = GetAllStarshipsQuery.Data(
                    GetAllStarshipsQuery.AllStarships(
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllStarshipsQuery.PageInfo(object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            starships = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = ApolloResponse.Builder(
                    operation = GetAllStarshipsQuery(),
                    UUID.randomUUID(),
                    data = allObjects
            ).build()

            val expected = listOf(UniversalChunk(object1.id, object1.name ?: "", object1.model ?: ""),
                    UniversalChunk(object2.id, object2.name ?: "", object2.model ?: ""))

            coEvery { apolloClient.query(GetAllStarshipsQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchStarships()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetAllVehiclesQuery object when fetch films then return list of universalChunk`(): Unit =
        runBlocking {
            //given
            val object1 = GetAllVehiclesQuery.Vehicle(
                    name = "Object1",
                    id = "id1",
                    model = "model1"
            )
            val object2 = GetAllVehiclesQuery.Vehicle(
                    name = "Object2",
                    id = "id2",
                    model = "model2"
            )
            val allObjects = GetAllVehiclesQuery.Data(
                    GetAllVehiclesQuery.AllVehicles(
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllVehiclesQuery.PageInfo(object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            vehicles = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = ApolloResponse.Builder(
                    operation = GetAllVehiclesQuery(),
                    UUID.randomUUID(),
                    data = allObjects
            ).build()

            val expected = listOf(UniversalChunk(object1.id, object1.name ?: "", object1.model ?: ""),
                    UniversalChunk(object2.id, object2.name ?: "", object2.model ?: ""))

            coEvery { apolloClient.query(GetAllVehiclesQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchVehicles()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetFilmQuery when fetchOneFilm then return Movie`(): Unit = runBlocking {
        //given
        val id = "objectId"
        val expected = Movie(id = id, title = "title", episodeId = "1", openingCrawl = "longText",
                releaseDate = "it was", director = "director", producers = listOf("producer1", "producer2"),
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                planetConnection = Connection(1, listOf(UniversalChunk(id = "planetId", name = "planetName"))),
                vehicleConnection = Connection(1, listOf(UniversalChunk(id = "vehicleId", name = "vehicleName"))),
                starshipConnection = Connection(1, listOf(UniversalChunk(id = "starshipId", name = "StarshipName"))),
                specieConnection = Connection(1, listOf(UniversalChunk(id = "connectId", name = "specieName"))))
        val data = GetFilmQuery.Data(GetFilmQuery.Film(
                title = "title", episodeID = 1, openingCrawl = "longText", director = "director",
                producers = listOf("producer1", "producer2", null),
                releaseDate = "it was",
                speciesConnection = GetFilmQuery.SpeciesConnection(1,
                        species = listOf(GetFilmQuery.Species("connectId", "specieName"), null)),
                starshipConnection = GetFilmQuery.StarshipConnection(1,
                        starships = listOf(GetFilmQuery.Starship("starshipId", "StarshipName"), null)),
                characterConnection = GetFilmQuery.CharacterConnection(1,
                        characters = listOf(GetFilmQuery.Character("charId", "charName"), null)),
                planetConnection = GetFilmQuery.PlanetConnection(1,
                        planets = listOf(GetFilmQuery.Planet("planetId", "planetName"), null)),
                vehicleConnection = GetFilmQuery.VehicleConnection(1,
                        vehicles = listOf(GetFilmQuery.Vehicle("vehicleId", "vehicleName"), null)),
                created = "was created", edited = null, id))
        val dataApolloResponse = ApolloResponse.Builder(
                operation = GetFilmQuery(),
                UUID.randomUUID(),
                data = data
        ).build()

        coEvery {
            apolloClient.query(GetFilmQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneFilm(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetPersonQuery when fetchOnePerson then return Person`(): Unit = runBlocking {
        //given
        val id = "objectId"
        val expected = Person(id = id, name = "name", homeworld = UniversalChunk(id = "planetId", name = "planetName"),
                specie = UniversalChunk(id = "connectId", name = "specieName"), birthYear = "it was",
                height = "12", mass = "120.0", gender = "gender", hair = "hair", skin = "skin",
                movieConnection = Connection(1, listOf(UniversalChunk(id = "planetId", name = "planetName"))),
                vehicleConnection = Connection(1, listOf(UniversalChunk(id = "vehicleId", name = "vehicleName"))),
                starshipConnection = Connection(1, listOf(UniversalChunk(id = "starshipId", name = "StarshipName"))))
        val data = GetPersonQuery.Data(GetPersonQuery.Person(
                name = "name", birthYear = "it was", gender = "gender", hairColor = "hair", height = 12,
                mass = 120.00, skinColor = "skin", homeworld = GetPersonQuery.Homeworld("planetName", "planetId"),
                eyeColor = "", species = GetPersonQuery.Species("specieName", "connectId"),
                filmConnection = GetPersonQuery.FilmConnection(1,
                        films = listOf(GetPersonQuery.Film("planetId", "planetName"), null)),
                starshipConnection = GetPersonQuery.StarshipConnection(1,
                        starships = listOf(GetPersonQuery.Starship("StarshipName", "starshipId"), null)),
                vehicleConnection = GetPersonQuery.VehicleConnection(1,
                        vehicles = listOf(GetPersonQuery.Vehicle("vehicleName", "vehicleId"), null)),
                created = "was created", edited = null, id = id))
        val dataApolloResponse = ApolloResponse.Builder(
                operation = GetPersonQuery(),
                UUID.randomUUID(),
                data = data
        ).build()

        coEvery {
            apolloClient.query(GetPersonQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOnePerson(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetPlanetQuery when fetchOnePlanet then return Planet`(): Unit = runBlocking {
        //given
        val id = "objectId"
        val expected = Planet(id = id, name = "name", diameter = "12", gravity = "gravity",
                population = "120.0", rotationPeriod = "15", orbitalPeriod = "15", climates = listOf("clim", "ates"),
                terrains = listOf("terrain"), surfaceWater = "100.0",
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "planetId", name = "planetName"))))
        val data = GetPlanetQuery.Data(GetPlanetQuery.Planet(
                name = "name", diameter = 12, rotationPeriod = 15, orbitalPeriod = 15, gravity = "gravity",
                population = 120.00, climates = listOf("clim", "ates", null), terrains = listOf("terrain", null),
                surfaceWater = 100.00, filmConnection = GetPlanetQuery.FilmConnection(1,
                films = listOf(GetPlanetQuery.Film("planetId", "planetName"), null)),
                residentConnection = GetPlanetQuery.ResidentConnection(1,
                        residents = listOf(GetPlanetQuery.Resident("charId", "charName"), null)),
                created = "was created", edited = null, id = id))
        val dataApolloResponse = ApolloResponse.Builder(
                operation = GetPlanetQuery(),
                UUID.randomUUID(),
                data = data
        ).build()

        coEvery {
            apolloClient.query(GetPlanetQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOnePlanet(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetSpecieQuery when fetchOneSpecie then return Specie`(): Unit = runBlocking {
        //given
        val id = "objectId"
        val expected = Specie(id = id, name = "name", classification = "class", designation = "design",
                averageHeight = "100.0", averageLifespan = "65", eyeColors = listOf("blue", "red"),
                hairColors = listOf("blue", "red"), skinColors = listOf("blue", "red"), language = "language",
                homeworld = UniversalChunk(id = "planetId", name = "planetName"),
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "planetId", name = "planetName"))))
        val data =
            GetSpecieQuery.Data(GetSpecieQuery.Species(name = "name", classification = "class", designation = "design",
                    averageHeight = 100.00, averageLifespan = 65, eyeColors = listOf("blue", "red", null),
                    hairColors = listOf("blue", "red", null), skinColors = listOf("blue", "red", null),
                    language = "language",
                    homeworld = GetSpecieQuery.Homeworld("planetId", "planetName"),
                    personConnection = GetSpecieQuery.PersonConnection(1,
                            people = listOf(GetSpecieQuery.Person("charId", "charName"), null)),
                    filmConnection = GetSpecieQuery.FilmConnection(1,
                            films = listOf(GetSpecieQuery.Film("planetId", "planetName"), null)),
                    created = "was created", edited = null, id = id))
        val dataApolloResponse = ApolloResponse.Builder(
                operation = GetSpecieQuery(),
                UUID.randomUUID(),
                data = data
        ).build()

        coEvery {
            apolloClient.query(GetSpecieQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneSpecie(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetVehicleQuery when fetchOneVehicle then return Vehicle`(): Unit = runBlocking {
        //given
        val id = "objectId"
        val expected = Vehicle(id = id, name = "name", model = "design", vehicleClass = "class",
                manufacturers = listOf("own", "creator"), cost = "120.0", crew = "yes", length = "80.0",
                passengers = "exists", vMax = "10", cargoCapacity = "100.0", consumables = "consume",
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "filmId", name = "filmTitle"))))
        val data =
            GetVehicleQuery.Data(GetVehicleQuery.Vehicle(name = "name", model = "design", vehicleClass = "class",
                    manufacturers = listOf("own", "creator", null), costInCredits = 120.00, crew = "yes",
                    length = 80.00,
                    passengers = "exists", maxAtmospheringSpeed = 10, cargoCapacity = 100.00, consumables = "consume",
                    pilotConnection = GetVehicleQuery.PilotConnection(1,
                            pilots = listOf(GetVehicleQuery.Pilot("charId", "charName"))),
                    filmConnection = GetVehicleQuery.FilmConnection(1,
                            films = listOf(GetVehicleQuery.Film("filmId", "filmTitle"))),
                    created = "was created", edited = null, id = id))
        val dataApolloResponse = ApolloResponse.Builder(
                operation = GetVehicleQuery(),
                UUID.randomUUID(),
                data = data
        ).build()

        coEvery {
            apolloClient.query(GetVehicleQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneVehicle(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetStarshipQuery when fetchOneStarship then return Starship`(): Unit = runBlocking {
        //given
        val id = "objectId"
        val expected = Starship(id = id, name = "name", model = "design", starshipClass = "class",
                manufacturers = listOf("own", "creator"), cost = "120.0", crew = "yes", length = "80.0",
                passengers = "exists", vMax = "10", cargoCapacity = "100.0", consumables = "consume",
                hyperdriveRating = "4.0", megalight = "20",
                characterConnection = Connection(1, listOf(UniversalChunk(id = "charId", name = "charName"))),
                movieConnection = Connection(1, listOf(UniversalChunk(id = "filmId", name = "filmTitle"))))
        val data =
            GetStarshipQuery.Data(GetStarshipQuery.Starship(name = "name", model = "design", starshipClass = "class",
                    manufacturers = listOf("own", "creator", null), costInCredits = 120.00, crew = "yes",
                    length = 80.00,
                    passengers = "exists", maxAtmospheringSpeed = 10, cargoCapacity = 100.00, consumables = "consume",
                    MGLT = 20, hyperdriveRating = 4.00,
                    pilotConnection = GetStarshipQuery.PilotConnection(1,
                            pilots = listOf(GetStarshipQuery.Pilot("charId", "charName"))),
                    filmConnection = GetStarshipQuery.FilmConnection(1,
                            films = listOf(GetStarshipQuery.Film("filmId", "filmTitle"))),
                    created = "was created", edited = null, id = id))
        val dataApolloResponse = ApolloResponse.Builder(
                operation = GetStarshipQuery(),
                UUID.randomUUID(),
                data = data
        ).build()

        coEvery {
            apolloClient.query(GetStarshipQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneStarship(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given error when fetchFilms then verify log call and return emptylist()`(): Unit = runBlocking {
        //given
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        coEvery { apolloClient.query(GetAllFilmsQuery()).execute() } throws IllegalArgumentException()

        //when
        val actual = dataFetcherImpl.fetchFilms()

        //then
        actual shouldBe listOf()
        verify(exactly = 1) { Log.e(any(), any()) }
    }
}