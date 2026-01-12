package com.begin_a_gain.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Long,
    val socialId: Long,
    val nickname: String,
    val refreshToken: String,
    val deleted: Boolean
)