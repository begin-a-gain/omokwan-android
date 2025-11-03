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