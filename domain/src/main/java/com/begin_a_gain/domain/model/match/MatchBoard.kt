package com.begin_a_gain.domain.model.match

import com.begin_a_gain.domain.enum.MatchCalendarStatus
import kotlinx.serialization.Serializable

@Serializable
data class MatchBoardInitialInfo(
    val users: List<MatchBoardUser>,
    val isTodayMatchCompleted: Boolean,
    val isTodayCombo: Boolean,
    val maxParticipants: Int,
    val matchTitle: String
)

@Serializable
data class MatchBoard(
    val users: List<MatchBoardUser>,
    val dates: List<MatchBoardDate>,
    val prevCursor: String,
    val nextCursor: String,
    val hasPrev: Boolean,
    val hasNext: Boolean,
    val isTodayMatchCompleted: Boolean
)

@Serializable
data class MatchBoardUser(
    val userId: Int,
    val nickname: String,
    val isHost: Boolean
)

@Serializable
data class MatchBoardDate(
    val date: String,
    val userStatus: List<MatchDatesUserStatus>
)

@Serializable
data class MatchDatesUserStatus(
    val userId: Int,
    val status: MatchCalendarStatus
)