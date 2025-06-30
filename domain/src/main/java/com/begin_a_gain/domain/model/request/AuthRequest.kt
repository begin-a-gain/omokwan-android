package com.begin_a_gain.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val accessToken: String
)