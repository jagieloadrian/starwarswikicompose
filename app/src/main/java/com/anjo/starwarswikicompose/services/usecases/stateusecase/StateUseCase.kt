package com.anjo.starwarswikicompose.services.usecases.stateusecase

import com.anjo.starwarswikicompose.services.usecases.stateusecase.notification.ReadNotificationsEnabledUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.notification.SaveNotificationsEnabledUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.onboarding.ReadOnboardingUseCase
import com.anjo.starwarswikicompose.services.usecases.stateusecase.onboarding.SaveOnboardingUseCase

data class StateUseCase(
        val saveOnboardingUseCase: SaveOnboardingUseCase,
        val readOnboardingUseCase: ReadOnboardingUseCase,
        val saveNotificationEnabled: SaveNotificationsEnabledUseCase,
        val readNotificationEnabled: ReadNotificationsEnabledUseCase,
)