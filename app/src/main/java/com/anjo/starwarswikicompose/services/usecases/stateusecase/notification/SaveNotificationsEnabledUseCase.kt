package com.anjo.starwarswikicompose.services.usecases.stateusecase.notification

import com.anjo.starwarswikicompose.services.data.repository.StateOperationRepository

class SaveNotificationsEnabledUseCase(
        private val operationRepository: StateOperationRepository
) {

    suspend operator fun invoke(enabled: Boolean) {
        operationRepository.saveNotificationEnabled(enabled)
    }
}