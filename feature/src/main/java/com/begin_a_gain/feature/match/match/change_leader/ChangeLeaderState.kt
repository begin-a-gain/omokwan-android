package com.begin_a_gain.feature.match.match.change_leader

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.ParticipantInfo

data class ChangeLeaderState(
    override val loadingCount: Int = 0,
    val participants: List<ParticipantInfo> = emptyList()
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}