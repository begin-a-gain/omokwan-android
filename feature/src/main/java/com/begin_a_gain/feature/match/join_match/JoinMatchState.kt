package com.begin_a_gain.feature.match.join_match

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.match.MatchInfo

data class JoinMatchState(
    override val loadingCount: Int = 0,
    val keyword: String = "",
    val categoryFilter: List<MatchCategoryItem> = listOf(),
    val availableMatchFilterSelected: Boolean = false,
    val selectedMatch: MatchInfo? = null,
    val isJoining: Boolean = false,
    val isMatchPasswordValid: Boolean = true
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface JoinMatchSideEffect {
    data class JoinSuccess(val matchId: Int) : JoinMatchSideEffect
}