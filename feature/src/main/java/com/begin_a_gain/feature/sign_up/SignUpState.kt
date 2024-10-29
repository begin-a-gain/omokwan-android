package com.begin_a_gain.feature.sign_up

enum class NicknameFailCase {
    Duplicated,
    Unconventional
}

data class SignUpState(
    val nickname: String = "",
    val nicknameValidation: Boolean = false,
    val nicknameFailCase: NicknameFailCase? = null
)

sealed class SignUpSideEffect {
    object SignUpSuccess: SignUpSideEffect()
}