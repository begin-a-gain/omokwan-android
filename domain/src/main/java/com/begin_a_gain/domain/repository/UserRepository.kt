package com.begin_a_gain.domain.repository

interface UserRepository {
    suspend fun postNicknameValidation(nickname: String): Result<Unit>
    suspend fun postNickname(nickname: String): Result<Unit>
}