package com.begin_a_gain.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int,
    val nickname: String
)

data class UserPaging(
    val users: List<User>,
    val nextCursor: String?,
    val hasNext: Boolean
)
