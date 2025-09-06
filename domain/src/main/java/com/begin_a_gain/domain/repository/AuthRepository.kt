package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.request.SignInRequest

interface AuthRepository {
    suspend fun kakaoSignIn(signInRequest: SignInRequest): Result<Boolean>
}