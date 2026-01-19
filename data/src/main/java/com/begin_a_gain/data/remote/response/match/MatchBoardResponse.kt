package com.begin_a_gain.data.remote.response.match

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
    val participantDays: Int,
    val participantNumbers: Int
)

@Serializable
data class MatchSettingsResponse(
    val name: String,
    val ongoingDays: Int,
    val matchCode: String,
    val repeatDayTypes: List<Int>,
    val maxParticipants: Int,
    val category: String,
    val password: String,
    val isPublic: Boolean
)