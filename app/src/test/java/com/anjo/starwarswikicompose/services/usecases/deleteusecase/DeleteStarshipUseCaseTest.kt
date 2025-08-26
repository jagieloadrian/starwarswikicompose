package com.anjo.starwarswikicompose.services.usecases.deleteusecase

import com.anjo.starwarswikicompose.services.data.repository.OperationRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeleteStarshipUseCaseTest {

    @MockK
    lateinit var operationRepository: OperationRepository

    @InjectMockKs
    lateinit var deleteUseCase: DeleteStarshipUseCase

    @Test
    fun `given id when deleteUseCase then verify call`() = runTest {
        //given
        val id = "new_id"
        coEvery { operationRepository.removeStarship(id) } just Runs

        //when
        deleteUseCase(id)

        //then
        coVerify { operationRepository.removeStarship(id) }
    }
}