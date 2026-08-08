package com.anjo.starwarswikicompose.services.apollofetcher

import android.util.Log
import com.anjo.starwarswikicompose.apollo.GetAllFilmsQuery
import com.anjo.starwarswikicompose.apollo.GetAllPeoplesQuery
import com.anjo.starwarswikicompose.apollo.GetAllPlanetsQuery
import com.anjo.starwarswikicompose.apollo.GetAllSpeciesQuery
import com.anjo.starwarswikicompose.apollo.GetAllStarshipsQuery
import com.anjo.starwarswikicompose.apollo.GetAllVehiclesQuery
import com.anjo.starwarswikicompose.apollo.GetFilmQuery
import com.anjo.starwarswikicompose.apollo.GetPersonQuery
import com.anjo.starwarswikicompose.apollo.GetPlanetQuery
import com.anjo.starwarswikicompose.apollo.GetSpecieQuery
import com.anjo.starwarswikicompose.apollo.GetStarshipQuery
import com.anjo.starwarswikicompose.apollo.GetVehicleQuery
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
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse.Builder
import com.apollographql.apollo.api.Optional
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
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
    fun `given response as null when fetchFilms from apollo then return empty list`() = runTest {
        //given
        val dataApolloResponse = Builder(
                operation = GetAllFilmsQuery(),
                requestUuid = UUID.randomUUID())
                .data(null)
                .build()

        val expected = listOf<UniversalChunkDto>()

        coEvery { apolloClient.query(GetAllFilmsQuery()).execute() } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchFilms()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetAllFilmsQuery object when fetch films then return list of universalChunk`(): Unit =
        runTest {
            //given
            val film1 = GetAllFilmsQuery.Film(
                    __typename = "Film",
                    title = "Film1",
                    episodeID = 1,
                    id = "movieId1"
            )
            val film2 = GetAllFilmsQuery.Film(
                    __typename = "Film",
                    title = "Film2",
                    episodeID = 2,
                    id = "movieId2"
            )
            val allFilms = GetAllFilmsQuery.Data(
                    GetAllFilmsQuery.AllFilms(
                            __typename = "AllFilms",
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllFilmsQuery.PageInfo("PageInfo", film1.id, hasNextPage = false, hasPreviousPage = false,
                                    film2.id),
                            films = listOf(film1, film2, null)
                    ))
            val dataApolloResponse = Builder(
                    operation = GetAllFilmsQuery(),
                    requestUuid = UUID.randomUUID())
                    .data(allFilms)
                    .build()

            val expected = listOf(UniversalChunkDto(film1.id, film1.title ?: "", film1.episodeID.toString(), FILMS),
                    UniversalChunkDto(film2.id, film2.title ?: "", film2.episodeID.toString(), FILMS))

            coEvery { apolloClient.query(GetAllFilmsQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchFilms()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response as null when fetchPeople from apollo then return empty list`() = runTest {
        //given
        val dataApolloResponse = Builder(
                operation = GetAllPeoplesQuery(),
                requestUuid = UUID.randomUUID())
                .data(null)
                .build()

        val expected = listOf<UniversalChunkDto>()

        coEvery { apolloClient.query(GetAllPeoplesQuery()).execute() } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchPeoples()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetAllPeoplesQuery object when fetch people then return list of universalChunk`(): Unit =
        runTest {
            //given
            val object1 = GetAllPeoplesQuery.Person(
                    __typename = "Person",
                    name = "Object1",
                    id = "id1",
                    birthYear = "12ABY"
            )
            val object2 = GetAllPeoplesQuery.Person(
                    __typename = "Person",
                    name = "Object2",
                    id = "id2",
                    birthYear = "22ABY"
            )
            val allObjects = GetAllPeoplesQuery.Data(
                    GetAllPeoplesQuery.AllPeople(
                            __typename = "AllPeople",
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllPeoplesQuery.PageInfo("PageInfo", object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            people = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = Builder(
                    operation = GetAllPeoplesQuery(),
                    requestUuid = UUID.randomUUID())
                    .data(allObjects)
                    .build()

            val expected = listOf(
                    UniversalChunkDto(object1.id, object1.name ?: "", object1.birthYear.toString(), PEOPLE),
                    UniversalChunkDto(object2.id, object2.name ?: "", object2.birthYear.toString(), PEOPLE))

            coEvery { apolloClient.query(GetAllPeoplesQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchPeoples()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response as null when fetchPlanets from apollo then return empty list`() = runTest {
        //given
        val dataApolloResponse = Builder(
                operation = GetAllPlanetsQuery(),
                requestUuid = UUID.randomUUID())
                .data(null)
                .build()

        val expected = listOf<UniversalChunkDto>()

        coEvery { apolloClient.query(GetAllPlanetsQuery()).execute() } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchPlanets()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetAllPlanetsQuery object when fetch planets then return list of universalChunk`(): Unit =
        runTest {
            //given
            val object1 = GetAllPlanetsQuery.Planet(
                    __typename = "Planet",
                    name = "Object1",
                    id = "id1",
                    population = 123.00
            )
            val object2 = GetAllPlanetsQuery.Planet(
                    __typename = "Planet",
                    name = "Object2",
                    id = "id2",
                    population = null
            )
            val allObjects = GetAllPlanetsQuery.Data(
                    GetAllPlanetsQuery.AllPlanets(
                            __typename = "AllPlanets",
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllPlanetsQuery.PageInfo("PageInfo", object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            planets = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = Builder(
                    operation = GetAllPlanetsQuery(),
                    requestUuid = UUID.randomUUID())
                    .data(allObjects)
                    .build()

            val expected = listOf(UniversalChunkDto(object1.id, object1.name ?: "", "123 citizens", PLANETS),
                    UniversalChunkDto(object2.id, object2.name ?: "", "0 citizens", PLANETS))

            coEvery { apolloClient.query(GetAllPlanetsQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchPlanets()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response as null when fetchSpecies from apollo then return empty list`() = runTest {
        //given
        val dataApolloResponse = Builder(
                operation = GetAllSpeciesQuery(),
                requestUuid = UUID.randomUUID())
                .data(null)
                .build()

        val expected = listOf<UniversalChunkDto>()

        coEvery { apolloClient.query(GetAllSpeciesQuery()).execute() } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchSpecies()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetAllSpeciesQuery object when fetch species then return list of universalChunk`(): Unit =
        runTest {
            //given
            val object1 = GetAllSpeciesQuery.Species(
                    __typename = "Species",
                    name = "Object1",
                    id = "id1",
                    language = "language"
            )
            val object2 = GetAllSpeciesQuery.Species(
                    __typename = "Species",
                    name = "Object2",
                    id = "id2",
                    language = "lang"
            )
            val allObjects = GetAllSpeciesQuery.Data(
                    GetAllSpeciesQuery.AllSpecies(
                            __typename = "AllSpecies",
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllSpeciesQuery.PageInfo("PageInfo", object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            species = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = Builder(
                    operation = GetAllSpeciesQuery(),
                    requestUuid = UUID.randomUUID())
                    .data(allObjects)
                    .build()

            val expected = listOf(UniversalChunkDto(object1.id, object1.name ?: "", object1.language ?: "", SPECIES),
                    UniversalChunkDto(object2.id, object2.name ?: "", object2.language ?: "", SPECIES))

            coEvery { apolloClient.query(GetAllSpeciesQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchSpecies()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response as null when fetchStarships from apollo then return empty list`() = runTest {
        //given
        val dataApolloResponse = Builder(
                operation = GetAllStarshipsQuery(),
                requestUuid = UUID.randomUUID())
                .data(null)
                .build()

        val expected = listOf<UniversalChunkDto>()

        coEvery { apolloClient.query(GetAllStarshipsQuery()).execute() } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchStarships()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetAllStarshipsQuery object when fetch starships then return list of universalChunk`(): Unit =
        runTest {
            //given
            val object1 = GetAllStarshipsQuery.Starship(
                    __typename = "Starship",
                    name = "Object1",
                    id = "id1",
                    model = "model1"
            )
            val object2 = GetAllStarshipsQuery.Starship(
                    __typename = "Starship",
                    name = "Object2",
                    id = "id2",
                    model = "model2"
            )
            val allObjects = GetAllStarshipsQuery.Data(
                    GetAllStarshipsQuery.AllStarships(
                            __typename = "AllStarships",
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllStarshipsQuery.PageInfo("PageInfo", object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            starships = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = Builder(
                    operation = GetAllStarshipsQuery(),
                    requestUuid = UUID.randomUUID())
                    .data(allObjects)
                    .build()

            val expected =
                listOf(UniversalChunkDto(object1.id, object1.name ?: "", object1.model ?: "", category = STARSHIPS),
                        UniversalChunkDto(object2.id, object2.name ?: "", object2.model ?: "", category = STARSHIPS))

            coEvery { apolloClient.query(GetAllStarshipsQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchStarships()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response as null when fetchVehicles from apollo then return empty list`() = runTest {
        //given
        val dataApolloResponse = Builder(
                operation = GetAllVehiclesQuery(),
                requestUuid = UUID.randomUUID())
                .data(null)
                .build()

        val expected = listOf<UniversalChunkDto>()

        coEvery { apolloClient.query(GetAllVehiclesQuery()).execute() } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchVehicles()

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetAllVehiclesQuery object when fetch vehicles then return list of universalChunk`(): Unit =
        runTest {
            //given
            val object1 = GetAllVehiclesQuery.Vehicle(
                    __typename = "Vehicle",
                    name = "Object1",
                    id = "id1",
                    model = "model1"
            )
            val object2 = GetAllVehiclesQuery.Vehicle(
                    __typename = "Vehicle",
                    name = "Object2",
                    id = "id2",
                    model = "model2"
            )
            val allObjects = GetAllVehiclesQuery.Data(
                    GetAllVehiclesQuery.AllVehicles(
                            __typename = "AllVehicles",
                            totalCount = 2,
                            edges = listOf(),
                            pageInfo = GetAllVehiclesQuery.PageInfo("PageInfo", object1.id, hasNextPage = false,
                                    hasPreviousPage = false,
                                    object2.id),
                            vehicles = listOf(object1, object2, null)
                    ))
            val dataApolloResponse = Builder(
                    operation = GetAllVehiclesQuery(),
                    requestUuid = UUID.randomUUID())
                    .data(allObjects)
                    .build()

            val expected =
                listOf(UniversalChunkDto(object1.id, object1.name ?: "", object1.model ?: "", category = VEHICLES),
                        UniversalChunkDto(object2.id, object2.name ?: "", object2.model ?: "", category = VEHICLES))

            coEvery { apolloClient.query(GetAllVehiclesQuery()).execute() } returns dataApolloResponse

            //when
            val actual = dataFetcherImpl.fetchVehicles()

            //then
            actual shouldBe expected
        }

    @Test
    fun `given response GetFilmQuery when fetchOneFilm then return Movie`(): Unit = runTest {
        //given
        val id = "objectId"
        val expected = MovieDto(id = id, title = "title", episodeId = "1", openingCrawl = "longText",
                releaseDate = "it was", director = "director", producers = listOf("producer1", "producer2"),
                characterConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "charId", name = "charName", category = PEOPLE))),
                planetConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "planetId", name = "planetName", category = PLANETS))),
                vehicleConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "vehicleId", name = "vehicleName", category = VEHICLES))),
                starshipConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "starshipId", name = "StarshipName", category = STARSHIPS))),
                specieConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "connectId", name = "specieName", category = SPECIES))))
        val data = GetFilmQuery.Data(GetFilmQuery.Film(
                __typename = "Film",
                title = "title", episodeID = 1, openingCrawl = "longText", director = "director",
                producers = listOf("producer1", "producer2", null),
                releaseDate = "it was",
                speciesConnection = GetFilmQuery.SpeciesConnection("SpeciesConnection", 1,
                        species = listOf(GetFilmQuery.Species("Species", "connectId", "specieName"), null)),
                starshipConnection = GetFilmQuery.StarshipConnection("StarshipConnection", 1,
                        starships = listOf(GetFilmQuery.Starship("Starship", "starshipId", "StarshipName"), null)),
                characterConnection = GetFilmQuery.CharacterConnection("CharacterConnection", 1,
                        characters = listOf(GetFilmQuery.Character("Character", "charId", "charName"), null)),
                planetConnection = GetFilmQuery.PlanetConnection("PlanetConnection", 1,
                        planets = listOf(GetFilmQuery.Planet("Planet", "planetId", "planetName"), null)),
                vehicleConnection = GetFilmQuery.VehicleConnection("VehicleConnection", 1,
                        vehicles = listOf(GetFilmQuery.Vehicle("Vehicle", "vehicleId", "vehicleName"), null)),
                created = "was created", edited = null, id = id))
        val dataApolloResponse = Builder(operation = GetFilmQuery(),
                requestUuid = UUID.randomUUID()).data(data = data
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
    fun `given response GetPersonQuery when fetchOnePerson then return Person`(): Unit = runTest {
        //given
        val id = "objectId"
        val expected = PersonDto(id = id, name = "name",
                homeworld = UniversalChunkDto(id = "planetId", name = "planetName", category = PLANETS),
                specie = UniversalChunkDto(id = "connectId", name = "specieName", category = SPECIES),
                birthYear = "it was",
                height = "12", mass = "120.0", gender = "gender", hair = "hair", skin = "skin",
                movieConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "planetId", name = "planetName", category = FILMS))),
                vehicleConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "vehicleId", name = "vehicleName", category = VEHICLES))),
                starshipConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "starshipId", name = "StarshipName", category = STARSHIPS))))
        val data = GetPersonQuery.Data(GetPersonQuery.Person(
                __typename = "Person",
                name = "name", birthYear = "it was", gender = "gender", hairColor = "hair", height = 12,
                mass = 120.00, skinColor = "skin", homeworld = GetPersonQuery.Homeworld("Homeworld", "planetName", "planetId"),
                eyeColor = "", species = GetPersonQuery.Species("Species", "specieName", "connectId"),
                filmConnection = GetPersonQuery.FilmConnection("FilmConnection", 1,
                        films = listOf(GetPersonQuery.Film("Film", "planetId", "planetName"), null)),
                starshipConnection = GetPersonQuery.StarshipConnection("StarshipConnection", 1,
                        starships = listOf(GetPersonQuery.Starship("Starship", "StarshipName", "starshipId"), null)),
                vehicleConnection = GetPersonQuery.VehicleConnection("VehicleConnection", 1,
                        vehicles = listOf(GetPersonQuery.Vehicle("Vehicle", "vehicleName", "vehicleId"), null)),
                created = "was created", edited = null, id = id))
        val dataApolloResponse = Builder(
                operation = GetPersonQuery(),
                requestUuid = UUID.randomUUID())
                .data(data)
                .build()

        coEvery {
            apolloClient.query(GetPersonQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOnePerson(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetPlanetQuery when fetchOnePlanet then return Planet`(): Unit = runTest {
        //given
        val id = "objectId"
        val expected = PlanetDto(id = id, name = "name", diameter = "12", gravity = "gravity",
                population = "120.0", rotationPeriod = "15", orbitalPeriod = "15", climates = listOf("clim", "ates"),
                terrains = listOf("terrain"), surfaceWater = "100.0",
                characterConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "charId", name = "charName", category = PEOPLE))),
                movieConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "planetId", name = "planetName", category = FILMS))))
        val data = GetPlanetQuery.Data(GetPlanetQuery.Planet(
                __typename = "Planet",
                name = "name", diameter = 12, rotationPeriod = 15, orbitalPeriod = 15, gravity = "gravity",
                population = 120.00, climates = listOf("clim", "ates", null), terrains = listOf("terrain", null),
                surfaceWater = 100.00, filmConnection = GetPlanetQuery.FilmConnection("FilmConnection", 1,
                films = listOf(GetPlanetQuery.Film("Film", "planetId", "planetName"), null)),
                residentConnection = GetPlanetQuery.ResidentConnection("ResidentConnection", 1,
                        residents = listOf(GetPlanetQuery.Resident("Resident", "charId", "charName"), null)),
                created = "was created", edited = null, id = id))
        val dataApolloResponse = Builder(
                operation = GetPlanetQuery(),
                requestUuid = UUID.randomUUID())
                .data(data)
                .build()

        coEvery {
            apolloClient.query(GetPlanetQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOnePlanet(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetSpecieQuery when fetchOneSpecie then return Specie`(): Unit = runTest {
        //given
        val id = "objectId"
        val expected = SpecieDto(id = id, name = "name", classification = "class", designation = "design",
                averageHeight = "100.0", averageLifespan = "65", eyeColors = listOf("blue", "red"),
                hairColors = listOf("blue", "red"), skinColors = listOf("blue", "red"), language = "language",
                homeworld = UniversalChunkDto(id = "planetId", name = "planetName", category = PLANETS),
                characterConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "charId", name = "charName", category = PEOPLE))),
                movieConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "planetId", name = "planetName", category = FILMS))))
        val data =
            GetSpecieQuery.Data(GetSpecieQuery.Species(
                    __typename = "Species",
                    name = "name", classification = "class", designation = "design",
                    averageHeight = 100.00, averageLifespan = 65, eyeColors = listOf("blue", "red", null),
                    hairColors = listOf("blue", "red", null), skinColors = listOf("blue", "red", null),
                    language = "language",
                    homeworld = GetSpecieQuery.Homeworld("Homeworld", "planetId", "planetName"),
                    personConnection = GetSpecieQuery.PersonConnection("PersonConnection", 1,
                            people = listOf(GetSpecieQuery.Person("Person", "charId", "charName"), null)),
                    filmConnection = GetSpecieQuery.FilmConnection("FilmConnection", 1,
                            films = listOf(GetSpecieQuery.Film("Film", "planetId", "planetName"), null)),
                    created = "was created", edited = null, id = id))
        val dataApolloResponse = Builder(
                operation = GetSpecieQuery(),
                requestUuid = UUID.randomUUID())
                .data(data)
                .build()

        coEvery {
            apolloClient.query(GetSpecieQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneSpecie(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetVehicleQuery when fetchOneVehicle then return Vehicle`(): Unit = runTest {
        //given
        val id = "objectId"
        val expected = VehicleDto(id = id, name = "name", model = "design", vehicleClass = "class",
                manufacturers = listOf("own", "creator"), cost = "120.0", crew = "yes", length = "80.0",
                passengers = "exists", vMax = "10", cargoCapacity = "100.0", consumables = "consume",
                characterConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "charId", name = "charName", category = PEOPLE))),
                movieConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "filmId", name = "filmTitle", category = FILMS))))
        val data =
            GetVehicleQuery.Data(GetVehicleQuery.Vehicle(
                    __typename = "Vehicle",
                    name = "name", model = "design", vehicleClass = "class",
                    manufacturers = listOf("own", "creator", null), costInCredits = 120.00, crew = "yes",
                    length = 80.00,
                    passengers = "exists", maxAtmospheringSpeed = 10, cargoCapacity = 100.00, consumables = "consume",
                    pilotConnection = GetVehicleQuery.PilotConnection("PilotConnection", 1,
                            pilots = listOf(GetVehicleQuery.Pilot("Pilot", "charId", "charName"))),
                    filmConnection = GetVehicleQuery.FilmConnection("FilmConnection", 1,
                            films = listOf(GetVehicleQuery.Film("Film", "filmId", "filmTitle"))),
                    created = "was created", edited = null, id = id))
        val dataApolloResponse = Builder(
                operation = GetVehicleQuery(),
                requestUuid = UUID.randomUUID())
                .data(data)
                .build()

        coEvery {
            apolloClient.query(GetVehicleQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneVehicle(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given response GetStarshipQuery when fetchOneStarship then return Starship`(): Unit = runTest {
        //given
        val id = "objectId"
        val expected = StarshipDto(id = id, name = "name", model = "design", starshipClass = "class",
                manufacturers = listOf("own", "creator"), cost = "120.0", crew = "yes", length = "80.0",
                passengers = "exists", vMax = "10", cargoCapacity = "100.0", consumables = "consume",
                hyperdriveRating = "4.0", megalight = "20",
                characterConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "charId", name = "charName", category = PEOPLE))),
                movieConnection = ConnectionDto(1,
                        listOf(UniversalChunkDto(id = "filmId", name = "filmTitle", category = FILMS))))
        val data =
            GetStarshipQuery.Data(GetStarshipQuery.Starship(
                    __typename = "Starship",
                    name = "name", model = "design", starshipClass = "class",
                    manufacturers = listOf("own", "creator", null), costInCredits = 120.00, crew = "yes",
                    length = 80.00,
                    passengers = "exists", maxAtmospheringSpeed = 10, cargoCapacity = 100.00, consumables = "consume",
                    MGLT = 20, hyperdriveRating = 4.00,
                    pilotConnection = GetStarshipQuery.PilotConnection("PilotConnection", 1,
                            pilots = listOf(GetStarshipQuery.Pilot("Pilot", "charId", "charName"))),
                    filmConnection = GetStarshipQuery.FilmConnection("FilmConnection", 1,
                            films = listOf(GetStarshipQuery.Film("Film", "filmId", "filmTitle"))),
                    created = "was created", edited = null, id = id))
        val dataApolloResponse = Builder(
                operation = GetStarshipQuery(),
                requestUuid = UUID.randomUUID())
                .data(data)
                .build()

        coEvery {
            apolloClient.query(GetStarshipQuery(id = Optional.presentIfNotNull(id))).execute()
        } returns dataApolloResponse

        //when
        val actual = dataFetcherImpl.fetchOneStarship(id)

        //then
        actual shouldBe expected
    }

    @Test
    fun `given error when fetchFilms then verify log call and return empty list`(): Unit = runTest {
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
