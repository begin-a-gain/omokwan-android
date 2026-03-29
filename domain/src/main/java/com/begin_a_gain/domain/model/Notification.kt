package com.begin_a_gain.domain.model

import com.begin_a_gain.domain.enum.NotificationType

data class Notification(
    val notificationId: Int,
    val type: NotificationType,
    val diffInMinutes: Int,
    val isRead: Boolean,
    val matchId: Int,
    val matchName: String,
    val isPublic: Boolean,
    val actorNickname: String,
    val prevHostNickname: String,
    val newHostNickname: String
)