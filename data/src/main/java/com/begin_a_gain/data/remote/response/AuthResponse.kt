package com.begin_a_gain.data.remote.response

data class SignInResponse(
    val accessToken: String,
    val signUpComplete: Boolean
)