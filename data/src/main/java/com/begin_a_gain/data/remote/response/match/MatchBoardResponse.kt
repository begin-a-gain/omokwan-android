package com.begin_a_gain.data.remote.response.match

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchBoardResponse(
    val match: MatchBoardInfoResponse,
    val users: List<MatchUserResponse>,
    val dates: List<MatchDatesResponse>,
    val prevCursor: String,
    val nextCursor: String,
    val hasPrev: Boolean,
    val hasNext: Boolean,
    val isTodayMatchCompleted: Boolean
)

@Serializable
data class MatchBoardInfoResponse(
    val matchName: String,
    val maxParticipants: Int
)

@Serializable
data class MatchUserResponse(
    val userId: Int,
    val nickname: String,
    val isHost: Boolean
)

@Serializable
data class MatchDatesResponse(
    val date: String,
    val userStatus: List<MatchDatesUserStatusResponse>
)

@Serializable
data class MatchDatesUserStatusResponse(
    val userId: Int,
    val isCompleted: Boolean,
    val streakCount: Int,
    val isCombo: Boolean
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