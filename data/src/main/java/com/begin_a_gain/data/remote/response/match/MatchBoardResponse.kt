package com.begin_a_gain.data.remote.response.match

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchBoardResponse(
    val users: List<MatchUserResponse>
)

@Serializable
data class MatchUserResponse(
    val userId: Int,
    val nickname: String,
    val isHost: Boolean
)

@Serializable
data class ParticipantsResponse(
    val userInfo: List<ParticipantInfoResponse>
)

@Serializable
data class ParticipantInfoResponse(
    val userId: Int,
    val nickname: String,
    val combo: Int,
    @SerialName("participantDays")
    val ongoingDays: Int,
    @SerialName("participantNumbers")
    val omokCount: Int
)

@Serializable
data class MatchSettingsResponse(
    val name: String,
    val ongoingDays: Int,
    val matchCode: String,
    val repeatDayTypes: List<Int>,
    val maxParticipants: Int,
    @SerialName("category")
    val categoryCode: String?,
    val password: String?,
    val isPublic: Boolean
)

@Serializable
data class ChangeHostResponse(
    val hostId: Int
)

@Serializable
data class DeleteParticipantResponse(
    val userId: Int
)

@Serializable
data class CompleteMatchResponse(
    val completed: Boolean
)