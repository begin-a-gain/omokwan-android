package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.get
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.read
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.unreadStatus
import com.begin_a_gain.data.remote.response.NotificationResponse
import com.begin_a_gain.data.remote.response.NotificationUnreadStatusResponse
import com.begin_a_gain.domain.model.request.NotificationReadRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import javax.inject.Inject

class NotificationApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getNotifications(): Response<NotificationResponse> {
        return client.get(ApiEndPoint.Notification.get()).body()
    }

    suspend fun getUnreadStatus(): Response<NotificationUnreadStatusResponse> {
        return client.get(ApiEndPoint.Notification.unreadStatus()).body()
    }

    suspend fun patchRead(notificationId :Int): Response<Unit> {
        return client.patch(ApiEndPoint.Notification.read()) {
            setBody(NotificationReadRequest(notificationId))
        }.body()
    }
}