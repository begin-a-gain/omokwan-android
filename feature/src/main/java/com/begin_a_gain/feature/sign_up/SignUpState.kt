package com.begin_a_gain.feature.sign_up

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.model.type.common.ValidationState
import com.begin_a_gain.util.enum.NicknameFailCase

data class SignUpState(
    override val loadingCount: Int = 0,
    val nickname: String = "",
    val nicknameValidation: ValidationState = ValidationState.Normal,
    val nicknameFailCase: NicknameFailCase? = null
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

sealed class SignUpSideEffect {
    object SignUpSuccess: SignUpSideEffect()
    object NavigateToSignIn: SignUpSideEffect()
}