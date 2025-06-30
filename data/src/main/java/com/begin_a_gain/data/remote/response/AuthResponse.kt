package com.begin_a_gain.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val accessToken: String,
    val signUpComplete: Boolean
)