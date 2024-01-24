package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.starwarswikicompose.domain.model.sw.Specie
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
class GetSpecieUseCaseTest{

    @MockK
    lateinit var operationRepository: OperationRepository
    @InjectMockKs
    lateinit var getSpecieUseCase: GetSpecieUseCase

    @Test
    fun `given specie when getSpecieUseCase then return specie`(): Unit = runBlocking {
        //given
        val objectId = "objectId"
        val expected = Specie(id = objectId, name = "title")
        coEvery { operationRepository.fetchOneSpecie(objectId) } returns expected

        //when
        val actual = getSpecieUseCase(objectId)

        //then
        actual shouldBe expected
    }
}