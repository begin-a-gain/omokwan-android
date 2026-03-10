package com.begin_a_gain.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val id: Int?,
    val socialId: Long?,
    val email: String?,
    val nickname: String?,
    val platform: String?,
    val refreshToken: String?,
    val deleted: Boolean?
)

@Serializable
data class UserMyPageResponse(
    val userId: Int,
    val nickname: String,
    val inProgressMatchCount: Int,
    val completedMatchCount: Int,
    val inProgressMatches: List<UserMyPageMatchItemResponse>,
    val completedMatches: List<UserMyPageMatchItemResponse>
)

@Serializable
data class UserMyPageMatchItemResponse(
    val matchId: Int,
    val matchName: String,
    val participantDays: Int,
    val comboCount: Int,
    @SerialName("participantNumbers")
    val omokCount: Int,
    val dayOfWeeks: List<Int>
)