package com.anjo.starwarswikicompose.services.usecases.operationusecase.movie

import com.anjo.starwarswikicompose.domain.model.sw.Movie
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetMovieUseCaseTest{
    @MockK
    lateinit var operationRepository: OperationRepository
    @InjectMockKs
    lateinit var getMovieUseCase: GetMovieUseCase

    @Test
    fun `given film when getMovieUseCase then return film`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Movie(id = objectId, title = "title")
        coEvery { operationRepository.fetchOneFilm(objectId) } returns expected

        //when
        val actual = getMovieUseCase(objectId)

        //then
        actual shouldBe expected
    }
}