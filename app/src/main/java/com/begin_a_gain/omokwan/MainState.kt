package com.begin_a_gain.omokwan

data class MainState(
    val isLoading: Boolean = false
)

sealed class MainSideEffect {
    data object Logout: MainSideEffect()
}