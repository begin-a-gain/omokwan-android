package com.begin_a_gain.feature.main.my_page.delete_account

import com.begin_a_gain.core.base.BaseState

data class DeleteAccountState(
    override val loadingCount: Int = 0,
    val reasons: Set<DeleteAccountReason> = emptySet(),
    val otherReason: String = ""
) : BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
    fun isSelectedOther(): Boolean = DeleteAccountReason.OTHER in reasons
}

enum class DeleteAccountReason(val message: String) {
    NOT_FREQUENTLY_USED("자주 사용하지 않아요."),
    MISSING_FEATURES("원하는 기능이 없어요."),
    TOO_COMPLEX("쓰기가 복잡해요."),
    USING_ANOTHER_APP("다른 앱을 쓰고 있어요."),
    OTHER("기타 (직접 입력)")
}

interface DeleteAccountSideEffect {
    object SuccessToDelete: DeleteAccountSideEffect
}