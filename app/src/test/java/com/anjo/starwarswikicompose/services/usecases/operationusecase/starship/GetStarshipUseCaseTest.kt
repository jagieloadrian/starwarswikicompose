package com.anjo.starwarswikicompose.services.usecases.operationusecase.starship

import com.anjo.starwarswikicompose.domain.model.sw.Starship
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
class GetStarshipUseCaseTest{
    @MockK
    lateinit var operationRepository: OperationRepository
    @InjectMockKs
    lateinit var getStarshipUseCase: GetStarshipUseCase

    @Test
    fun `given starship when fetchOneStarship then return starship`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Starship(id = objectId, name = "title")
        coEvery { operationRepository.fetchOneStarship(objectId) } returns expected

        //when
        val actual = getStarshipUseCase(objectId)

        //then
        actual shouldBe expected
    }
}
