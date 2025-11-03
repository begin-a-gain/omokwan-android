package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.user.UserInfo

interface UserRepository {
    suspend fun postNicknameValidation(nickname: String): Result<Unit>
    suspend fun postNickname(nickname: String): Result<Unit>
    suspend fun getUserInfo (): Result<UserInfo>
}