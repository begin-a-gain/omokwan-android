package com.begin_a_gain.domain.model

import kotlinx.serialization.Serializable

data class MatchHistory(
    val matchId: String,
    val matchName: String,
    val matchDays: Int,
    val ownerId: String,
    val membersHistory: List<ParticipantInfo>
)

@Serializable
data class ParticipantInfo(
    val id: Int,
    val name: String,
    val combo: Int,
    val omok: Int,
    val days: Int,
    val isHost: Boolean = false
)