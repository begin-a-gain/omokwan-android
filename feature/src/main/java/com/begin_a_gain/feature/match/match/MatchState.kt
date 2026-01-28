package com.begin_a_gain.feature.match.match

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.MemberInfo

data class MatchState(
    override val loadingCount: Int = 0,
    val todayDone: Boolean = false,
    val amIHost: Boolean = false,
    val participants: List<MemberInfo> = emptyList()
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface MatchSideEffect {
    object ShowInitialToast : MatchSideEffect
    data class SuccessToKickMember(val name: String) : MatchSideEffect
}