package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.remote.api.NotificationApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.enum.NotificationType.Companion.parse
import com.begin_a_gain.domain.model.Notification
import com.begin_a_gain.domain.repository.NotificationRepository
import com.begin_a_gain.util.common.DateTimeUtil
import com.begin_a_gain.util.common.DateTimeUtil.toDateTime
import com.begin_a_gain.util.common.DateTimeUtil.toIsoDateTime
import org.joda.time.DateTime
import org.joda.time.Minutes
import javax.inject.Inject

class NotificationRepositoryImpl @Inject internal constructor(
    private val notificationApi: NotificationApi
): NotificationRepository {
    override suspend fun getNotifications(): Result<List<Notification>> {
        return callApi(
            call = {
                notificationApi.getNotifications()
            },
            handleResponse = { response ->
                response?.notifications?.map { notification ->
                    val dateTime = notification.occurredAt.toIsoDateTime()
                    val now = DateTime.now()
                    Notification(
                        notificationId = notification.notificationId,
                        type = notification.type.parse(),
                        isRead = notification.isRead,
                        diffInMinutes = Minutes.minutesBetween(dateTime, now).minutes,
                        matchName = notification.matchName,
                        matchId = notification.matchId,
                        isPublic = notification.isPublic,
                        actorNickname = notification.actorNickname?: "",
                        prevHostNickname = notification.prevHostNickname?: "",
                        newHostNickname = notification.newHostNickname?: ""
                    )
                }?: emptyList()
            }
        )
    }

    override suspend fun getUnreadStatus(): Result<Boolean> {
        return callApi(
            call = {
                notificationApi.getUnreadStatus()
            },
            handleResponse = { response ->
                response?.hasBadge?: false
            }
        )
    }

    override suspend fun patchRead(notificationId: Int): Result<Unit> {
        return callApi(
            call = {
                notificationApi.patchRead(notificationId)
            },
            handleResponse = { }
        )
    }
}