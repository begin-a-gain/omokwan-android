package com.begin_a_gain.domain.model.match

import com.begin_a_gain.model.type.match.MatchStatus
import kotlinx.serialization.Serializable

@Serializable
data class MatchItem(
    val matchId: Int = 0,
    val name: String = "",
    val ongoingDays: Int = 0,
    val participants: Int = 0,
    val maxParticipants: Int = 5,
    val public: Boolean = true,
    val status: MatchStatus = MatchStatus.None
)