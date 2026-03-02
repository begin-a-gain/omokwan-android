package com.begin_a_gain.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class NicknameValidationResponse(
    val isValid: Boolean?,
    val isDuplicated: Boolean?
)