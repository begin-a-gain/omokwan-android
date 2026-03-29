package com.begin_a_gain.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val accessToken: String
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)

@Serializable
data class DeletionSurveyRequest(
    val reasons: List<String>,
    val otherReason: String
)