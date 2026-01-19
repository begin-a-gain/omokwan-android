package com.begin_a_gain.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MatchCategoryItemResponse(
    val code: String,
    val category: String,
    val emoji: String
)

@Serializable
data class CreateMatchResponse(
    val matchId: Int
)

@Serializable
data class MyDailyMatchResponse(
    val matchId: Int,
    val name: String,
    val ongoingDays: Int,
    val participants: Int,
    val maxParticipants: Int,
    val completed: Boolean,
    val public: Boolean
)

@Serializable
data class MatchListResponse(
    val matchList: List<MatchItemResponse>?,
    val hasNext: Boolean?
)

@Serializable
data class MatchItemResponse(
    val matchId: Int,
    val categoryId: Int,
    val name: String,
    val hostName: String,
    val ongoingDays: Int,
    val maxParticipants: Int,
    val participants: Int,
    val joinable: String,
    val public: Boolean
)

@Serializable
data class JoinMatchResponse(
    val matchId: String?
)

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