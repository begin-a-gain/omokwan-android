package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.remote.api.NotificationApi
import com.begin_a_gain.domain.model.Notification
import com.begin_a_gain.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject internal constructor(
    private val notificationApi: NotificationApi
): NotificationRepository {
    override suspend fun getNotifications(): Result<List<Notification>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUnreadStatus(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun patchRead(notificationId: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

}