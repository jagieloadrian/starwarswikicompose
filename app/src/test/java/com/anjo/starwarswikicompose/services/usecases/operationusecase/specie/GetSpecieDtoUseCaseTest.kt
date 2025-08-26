package com.anjo.starwarswikicompose.services.usecases.operationusecase.specie

import com.anjo.starwarswikicompose.domain.dto.SpecieDto
import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetSpecieDtoUseCaseTest {

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var getSpecieUseCase: GetSpecieUseCase

    @Test
    fun `given specie when getSpecieUseCase then return specie`(): Unit = runTest {
        //given
        val objectId = "objectId"
        val expected = SpecieDto(id = objectId, name = "title")
        coEvery { operationRepository.fetchOneSpecie(objectId) } returns expected

        //when
        val actual = getSpecieUseCase(objectId)

        //then
        actual shouldBe expected
    }
}