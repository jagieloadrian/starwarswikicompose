package com.anjo.starwarswikicompose.services.data.repository

import com.anjo.starwarswikicompose.services.data.repository.datastore.DataStoreOperations
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class StateOperationRepositoryTest {

    @RelaxedMockK
    lateinit var dataStoreOperations: DataStoreOperations

    @InjectMockKs
    lateinit var operationRepository: StateOperationRepository

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveOnboardingState then verify call`(boolean: Boolean) = runTest {
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
    fun `given boolean when readOnboardingState then return expected response`(boolean: Boolean) = runTest {
        //given
        val expected = flow { emit(boolean) }

        coEvery { dataStoreOperations.readingBoardingState() } returns expected
        //when
        val actual = operationRepository.readOnboardingState()

        //then
        actual shouldBe expected
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveNotificationEnabled then verify call`(boolean: Boolean) = runTest {
        //given
        val slot = slot<Boolean>()

        coEvery { dataStoreOperations.saveNotificationEnabled(capture(slot)) } returns Unit
        //when
        operationRepository.saveNotificationEnabled(boolean)

        //then
        slot.captured shouldBe boolean
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when readNotificationEnabled then return expected response`(boolean: Boolean) = runTest {
        //given
        val expected = flow { emit(boolean) }

        coEvery { dataStoreOperations.readNotificationEnabled() } returns expected
        //when
        val actual = operationRepository.readNotificationEnabled()

        //then
        actual shouldBe expected
    }
}