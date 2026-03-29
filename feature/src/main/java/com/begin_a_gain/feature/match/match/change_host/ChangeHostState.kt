package com.begin_a_gain.feature.match.match.change_host

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.MemberInfo

data class ChangeHostState(
    override val loadingCount: Int = 0,
    val participants: List<MemberInfo> = emptyList(),
    val selectedIndex: Int = -1
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface ChangeHostSideEffect {
    data class ChangeSuccess(val newHostName: String): ChangeHostSideEffect
}