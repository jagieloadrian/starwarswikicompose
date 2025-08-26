@file:OptIn(ExperimentalCoroutinesApi::class)

package com.anjo.starwarswikicompose.presentation.screens.create

import com.anjo.starwarswikicompose.domain.dto.MovieDto
import com.anjo.starwarswikicompose.domain.dto.PersonDto
import com.anjo.starwarswikicompose.domain.dto.PlanetDto
import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.domain.dto.StarshipDto
import com.anjo.starwarswikicompose.domain.dto.UniversalChunkDto
import com.anjo.starwarswikicompose.domain.dto.VehicleDto
import com.anjo.starwarswikicompose.domain.model.sw.Category
import com.anjo.starwarswikicompose.domain.model.sw.Category.ALL
import com.anjo.starwarswikicompose.domain.model.sw.Category.FILMS
import com.anjo.starwarswikicompose.domain.model.sw.Category.PEOPLE
import com.anjo.starwarswikicompose.domain.model.sw.Category.PLANETS
import com.anjo.starwarswikicompose.domain.model.sw.Category.SPECIES
import com.anjo.starwarswikicompose.domain.model.sw.Category.STARSHIPS
import com.anjo.starwarswikicompose.domain.model.sw.Category.VEHICLES
import com.anjo.starwarswikicompose.domain.model.sw.SourceType
import com.anjo.starwarswikicompose.services.usecases.insertusecase.InsertUseCases
import com.anjo.starwarswikicompose.services.usecases.operationusecase.UseCases
import com.anjo.starwarswikicompose.tools.TestTools
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(MockKExtension::class)
class AddObjectViewModelTest {

    @RelaxedMockK
    private lateinit var useCases: UseCases

    @RelaxedMockK
    private lateinit var insertUseCases: InsertUseCases

    @InjectMockKs
    private lateinit var addObjectViewModel: AddObjectViewModel

    private val testDispatcher = StandardTestDispatcher()

    @ParameterizedTest
    @MethodSource("chunksAndCategory")
    fun `given movies when getChunksByCategory then return properly updated property and verify call`(
            expected: List<UniversalChunkDto>,
            category: Category) = runTest(testDispatcher) {
        //given
        coEvery { returnUseCaseFunctionByCategory(category) } returns expected

        //when
        addObjectViewModel.getChunksByCategory(category)
        advanceUntilIdle()

        val actual = getPropertyByCategory(category)

        //then
        actual shouldBe expected
        coVerify { returnUseCaseFunctionByCategory(category) }
    }

    @Test
    fun `given movieDto when insertMovie then currentObjectId should be updated also verify call`() =
        runTest(testDispatcher) {
            //given
            val dto = MovieDto(id = "movieId1", title = "movie1", episodeId = "movieDesc1")
            val chunk = UniversalChunkDto("movieId1", "movie1", "movieDesc1", FILMS,
                    sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertMovieUseCase(dto) } returns dto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertMovie(dto)
            advanceUntilIdle()

            val actual = addObjectViewModel.currentObjectId.value

            //then
            actual shouldBe dto.id
            coVerify { insertUseCases.insertMovieUseCase(dto) }
            coVerify { insertUseCases.insertChunkUseCase(chunk) }
        }

    @Test
    fun `given personDto when insertPerson then currentObjectId should be updated also verify call`() =
        runTest(testDispatcher) {
            //given
            val dto = PersonDto(id = "personId1", name = "person1", birthYear = "personDesc1")
            val chunk = UniversalChunkDto("personId1", "person1", "personDesc1", PEOPLE,
                    sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertPersonUseCase(dto) } returns dto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertPerson(dto)
            advanceUntilIdle()

            val actual = addObjectViewModel.currentObjectId.value

            //then
            actual shouldBe dto.id
            coVerify { insertUseCases.insertPersonUseCase(dto) }
            coVerify { insertUseCases.insertChunkUseCase(chunk) }
        }

    @Test
    fun `given planetDto when insertPlanet then currentObjectId should be updated also verify call`() =
        runTest(testDispatcher) {
            //given
            val dto = PlanetDto(id = "planetId1", name = "planet1", population = "planetDesc1")
            val chunk = UniversalChunkDto("planetId1", "planet1", "planetDesc1", PLANETS,
                    sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertPlanetUseCase(dto) } returns dto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertPlanet(dto)
            advanceUntilIdle()

            val actual = addObjectViewModel.currentObjectId.value

            //then
            actual shouldBe dto.id
            coVerify { insertUseCases.insertPlanetUseCase(dto) }
            coVerify { insertUseCases.insertChunkUseCase(chunk) }
        }

    @Test
    fun `given specieDto when insertSpecie then currentObjectId should be updated also verify call`() =
        runTest(testDispatcher) {
            //given
            val dto = SpecieDto(id = "specieId1", name = "specie1", language = "specieDesc1")
            val chunk = UniversalChunkDto("specieId1", "specie1", "specieDesc1", SPECIES,
                    sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertSpecieUseCase(dto) } returns dto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertSpecie(dto)
            advanceUntilIdle()

            val actual = addObjectViewModel.currentObjectId.value

            //then
            actual shouldBe dto.id
            coVerify { insertUseCases.insertSpecieUseCase(dto) }
            coVerify { insertUseCases.insertChunkUseCase(chunk) }
        }

    @Test
    fun `given starshipDto when insertStarship then currentObjectId should be updated also verify call`() =
        runTest(testDispatcher) {
            //given
            val dto = StarshipDto(id = "starshipId1", name = "starship1", model = "starshipDesc1")
            val chunk =
                UniversalChunkDto("starshipId1", "starship1", "starshipDesc1", STARSHIPS,
                        sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertStarshipUseCase(dto) } returns dto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertStarship(dto)
            advanceUntilIdle()

            val actual = addObjectViewModel.currentObjectId.value

            //then
            actual shouldBe dto.id
            coVerify { insertUseCases.insertStarshipUseCase(dto) }
            coVerify { insertUseCases.insertChunkUseCase(chunk) }
        }

    @Test
    fun `given vehicleDto when insertVehicle then currentObjectId should be updated also verify call`() =
        runTest(testDispatcher) {
            //given
            val dto = VehicleDto(id = "vehicleId1", name = "vehicle1", model = "vehicleDesc1")
            val chunk = UniversalChunkDto("vehicleId1", "vehicle1", "vehicleDesc1", VEHICLES,
                    sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertVehicleUseCase(dto) } returns dto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertVehicle(dto)
            advanceUntilIdle()

            val actual = addObjectViewModel.currentObjectId.value

            //then
            actual shouldBe dto.id
            coVerify { insertUseCases.insertVehicleUseCase(dto) }
            coVerify { insertUseCases.insertChunkUseCase(chunk) }
        }

    @Test
    fun `given dto when cleanCurrentObjectId then property currentObjectId should be empty`() =
        runTest(testDispatcher) {
            //given
            val movieDto = MovieDto("movieId1", "movie1", "movieDesc1")
            val chunk = UniversalChunkDto("movieId1", "movie1", "movieDesc1", FILMS,
                    sourceType = SourceType.ROOM)
            coEvery { insertUseCases.insertMovieUseCase(movieDto) } returns movieDto.id
            coEvery { insertUseCases.insertChunkUseCase(chunk) } returns ""

            //when
            addObjectViewModel.insertMovie(movieDto)
            advanceUntilIdle()
            addObjectViewModel.cleanCurrentObjectId()

            //then
            val actual = addObjectViewModel.currentObjectId.value

            actual.shouldBeEmpty()
        }

    private suspend fun returnUseCaseFunctionByCategory(category: Category): List<UniversalChunkDto> {
        return when (category) {
            ALL       -> throw Exception()
            FILMS     -> useCases.getAllFilmsUseCase()
            PEOPLE    -> useCases.getAllPeopleUseCase()
            PLANETS   -> useCases.getAllPlanetsUseCase()
            SPECIES   -> useCases.getAllSpeciesUseCase()
            STARSHIPS -> useCases.getAllStarshipsUseCase()
            VEHICLES  -> useCases.getAllVehicleUseCase()
        }
    }

    private fun getPropertyByCategory(category: Category): List<UniversalChunkDto> {
        return when (category) {
            ALL       -> listOf()
            FILMS     -> addObjectViewModel.movieChunks.value.chunks
            PEOPLE    -> addObjectViewModel.charChunks.value.chunks
            PLANETS   -> addObjectViewModel.planetChunks.value.chunks
            SPECIES   -> addObjectViewModel.specieChunks.value.chunks
            STARSHIPS -> addObjectViewModel.starshipChunks.value.chunks
            VEHICLES  -> addObjectViewModel.vehicleChunks.value.chunks
        }
    }

    companion object {
        @JvmStatic
        fun chunksAndCategory(): List<Arguments> {
            return listOf(
                    Arguments.of(TestTools.movies, FILMS),
                    Arguments.of(TestTools.planet, PLANETS),
                    Arguments.of(TestTools.specie, SPECIES),
                    Arguments.of(TestTools.people, PEOPLE),
                    Arguments.of(TestTools.starship, STARSHIPS),
                    Arguments.of(TestTools.vehicle, VEHICLES)
            )
        }
    }
}