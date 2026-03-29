package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.Notification

interface NotificationRepository {
    suspend fun getNotifications(): Result<List<Notification>>
    suspend fun getUnreadStatus(): Result<Boolean>
    suspend fun patchRead(notificationId :Int?): Result<Unit>
}