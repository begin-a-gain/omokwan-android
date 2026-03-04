package com.begin_a_gain.feature.main.my_page

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.user.MyPageMatchItem

data class MyPageState(
    override val loadingCount: Int = 0,
    val nickname: String = "",
    val inProgressMatches: List<MyPageMatchItem> = emptyList(),
    val completedMatches: List<MyPageMatchItem> = emptyList()
) : BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}