package com.anjo.starwarswikicompose.services.usecases.stateusecase.notification

import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(MockKExtension::class)
class SaveNotificationsEnabledUseCaseTest {

    @MockK
    lateinit var operationRepository: StateOperationRepository

    @InjectMockKs
    lateinit var saveOnboardingUseCase: SaveNotificationsEnabledUseCase

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `given boolean when saveOnboardingUseCase then verify call`(boolean: Boolean) = runTest {
        //given
        val slot = slot<Boolean>()

        coEvery { operationRepository.saveNotificationEnabled(capture(slot)) } returns Unit
        //when
        saveOnboardingUseCase(boolean)

        //then
        slot.captured shouldBe boolean
    }

}