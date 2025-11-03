package com.begin_a_gain.feature.splash

import com.begin_a_gain.core.base.BaseState

data class SplashState(
    override val loadingCount: Int = 0,
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}


sealed class SplashSideEffect {
    data object NotFinishedSignUp: SplashSideEffect()
    data object LoggedIn: SplashSideEffect()
}