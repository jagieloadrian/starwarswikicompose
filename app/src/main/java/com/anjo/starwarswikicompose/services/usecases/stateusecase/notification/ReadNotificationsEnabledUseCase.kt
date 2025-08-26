package com.anjo.starwarswikicompose.services.usecases.stateusecase.notification

import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository
import kotlinx.coroutines.flow.Flow

class ReadNotificationsEnabledUseCase(
        private val operationRepository: StateOperationRepository,
) {

    operator fun invoke(): Flow<Boolean> {
        return operationRepository.readNotificationEnabled()
    }
}