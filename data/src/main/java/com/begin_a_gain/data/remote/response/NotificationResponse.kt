package com.begin_a_gain.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val notifications: List<NotificationItemResponse>
)

@Serializable
data class NotificationItemResponse(
    val notificationId: Int,
    val type: String,
    val isRead: Boolean,
    val occurredAt: String,
    val matchName: String,
    val matchId: Int,
    val isPublic: Boolean,
    val actorNickname: String,
    val prevHostNickname: String,
    val newHostNickname: String
)

@Serializable
data class NotificationUnreadStatusResponse(
    val hasBadge: Boolean
)