package com.begin_a_gain.feature.sign_in

data class SignInState(
    val accessToken: String = ""
)

sealed class SignInSideEffect {
    data object signUp: SignInSideEffect()
    data object SignInFailed: SignInSideEffect()
    data object NavigateToSignUp: SignInSideEffect()
    data object NavigateToMain: SignInSideEffect()
}