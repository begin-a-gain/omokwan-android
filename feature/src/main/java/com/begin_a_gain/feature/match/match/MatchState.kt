package com.begin_a_gain.feature.match.match

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.ParticipantInfo

data class MatchState(
    override val loadingCount: Int = 0,
    val todayDone: Boolean = false,
    val participants: List<ParticipantInfo> = emptyList()
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}