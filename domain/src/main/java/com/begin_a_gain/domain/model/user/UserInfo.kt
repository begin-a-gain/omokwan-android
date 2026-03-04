package com.begin_a_gain.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    val socialId: Long,
    val nickname: String,
    val refreshToken: String,
    val deleted: Boolean
)

@Serializable
data class MyPageInfo(
    val nickname: String,
    val inProgressMatchList: List<MyPageMatchItem>,
    val completedMatchList: List<MyPageMatchItem>
)

@Serializable
data class MyPageMatchItem(
    val matchId: Int = -1,
    val title: String = "",
    val ongoingDays: Int = 0,
    val combo: Int = 0,
    val omok: Int = 0,
    val repeatDays: List<Int> = emptyList()
)