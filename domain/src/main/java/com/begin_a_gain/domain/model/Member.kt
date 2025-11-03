package com.begin_a_gain.domain.model

data class MatchHistory(
    val matchId: String,
    val matchName: String,
    val matchDays: Int,
    val ownerId: String,
    val membersHistory: List<MemberHistory>
)

data class MemberHistory(
    val id: String,
    val name: String,
    val combo: Int,
    val omok: Int,
    val days: Int
)