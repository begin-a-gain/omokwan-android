package com.begin_a_gain.feature.sign_up

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.core.type.ValidationState

enum class NicknameFailCase(val message: String) {
    Duplicated("이미 사용중인 아이디 입니다."),
    Unconventional("2~10글자 사이의 한글 혹은 영문만 사용해주세요.")
}

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