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