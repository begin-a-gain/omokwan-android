package com.begin_a_gain.feature.main.my_page.delete_account

import com.begin_a_gain.core.base.BaseState

data class DeleteAccountState(
    override val loadingCount: Int = 0,
    val reasons: Set<Int> = emptySet(),
    val otherReason: String = ""
) : BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}
