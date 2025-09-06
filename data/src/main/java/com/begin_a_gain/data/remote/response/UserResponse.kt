package com.begin_a_gain.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val id: Long?,
    val socialId: Long?,
    val email: String?,
    val nickname: String?,
    val platform: String?,
    val refreshToken: String?,
    val deleted: Boolean?
)