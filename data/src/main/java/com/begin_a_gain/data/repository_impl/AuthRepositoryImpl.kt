package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.local.TokenManager
import com.begin_a_gain.data.remote.api.AuthApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.model.request.SignInRequest
import com.begin_a_gain.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun kakaoSignIn(signInRequest: SignInRequest): Result<Boolean> {
        return callApi(
            call = {
                authApi.kakaoSignIn(signInRequest)
            },
            handleResponse = { response ->
                if (response == null) null
                else {
                    tokenManager.saveAccessToken(response.accessToken)
                    response.signUpComplete
                }
            }
        )
    }
}