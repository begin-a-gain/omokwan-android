package com.begin_a_gain.omokwang

data class MainState(
    val isLoading: Boolean = false
)

sealed class MainSideEffect {
    data object Logout: MainSideEffect()
}