package com.begin_a_gain.domain.model.match

import com.begin_a_gain.model.type.match.MatchStatus
import kotlinx.serialization.Serializable

@Serializable
data class MyMatchItem(
    val matchId: Int = 0,
    val name: String = "",
    val ongoingDays: Int = 0,
    val participants: Int = 0,
    val maxParticipants: Int = 5,
    val public: Boolean = true,
    val status: MatchStatus = MatchStatus.None
)

@Serializable
data class MatchInfo(
    val matchId: Int = 0,
    val name: String = "",
    val ongoingDays: Int = 0,
    val participants: Int = 0,
    val maxParticipants: Int = 5,
    val category: String = "",
    val public: Boolean = true,
    val joinable: Boolean = true,
    val owner: String = ""
)