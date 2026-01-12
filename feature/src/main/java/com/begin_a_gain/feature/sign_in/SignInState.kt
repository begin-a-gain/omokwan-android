package com.begin_a_gain.feature.sign_in

import com.begin_a_gain.core.base.BaseState

data class SignInState(
    override val loadingCount: Int = 0,
    val accessToken: String = ""
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

sealed class SignInSideEffect {
    data object SignInFailed: SignInSideEffect()
    data object NavigateToSignUp: SignInSideEffect()
    data object NavigateToMain: SignInSideEffect()
}