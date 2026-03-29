package com.begin_a_gain.feature.main.my_page.change_nickname

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.model.type.common.ValidationState
import com.begin_a_gain.util.enum.NicknameFailCase

data class ChangeNicknameState(
    override val loadingCount: Int = 0,
    val nickname: String = "",
    val nicknameValidation: ValidationState = ValidationState.Normal,
    val nicknameFailCase: NicknameFailCase? = null
) : BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

sealed class ChangeNicknameSideEffect {
    object SuccessToChange: ChangeNicknameSideEffect()
}