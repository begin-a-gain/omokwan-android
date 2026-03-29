package com.begin_a_gain.feature.match.match

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.MemberInfo
import com.begin_a_gain.domain.model.match.MatchBoardDate

data class MatchState(
    override val loadingCount: Int = 0,
    val todayDone: Boolean = false,
    val amIHost: Boolean = false,
    val participants: List<MemberInfo> = emptyList(),
    val maxParticipants: Int = 5,
    val matchTitle: String = ""
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface MatchSideEffect {
    object ShowInitialToast : MatchSideEffect
    data class SuccessToKickMember(val name: String) : MatchSideEffect
    object SuccessToCompleteOmok : MatchSideEffect
    data class ShowCombo(val combo: Int): MatchSideEffect
}

sealed class CalendarItem {
    data class Header(val title: String) : CalendarItem()
    data class Day(val data: MatchBoardDate) : CalendarItem()
}