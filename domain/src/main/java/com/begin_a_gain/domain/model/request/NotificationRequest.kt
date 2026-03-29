package com.begin_a_gain.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class NotificationReadRequest(
    val notificationId: Int?
)