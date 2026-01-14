package com.begin_a_gain.domain.model.match

import kotlinx.serialization.Serializable

@Serializable
data class MatchBoard(
    val users: List<MatchUser>,
)

@Serializable
data class MatchUser(
    val userId: Int,
    val nickname: String,
    val isHost: Boolean,
)