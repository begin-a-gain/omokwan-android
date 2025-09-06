package com.begin_a_gain.data.remote.api

import android.util.Log
import com.begin_a_gain.data.local.TokenManager
import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.kakaoSignIn
import com.begin_a_gain.data.remote.response.SignInResponse
import com.begin_a_gain.domain.model.request.SignInRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.parseServerSetCookieHeader
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient,
    private val tokenManager: TokenManager
) {
    suspend fun kakaoSignIn(signInRequest: SignInRequest): Response<SignInResponse> {
        val httpResponse = client.post(ApiEndPoint.Auth.kakaoSignIn()) {
            setBody(signInRequest)
        }

        httpResponse.headers.getAll("Set-Cookie")
            ?.map { parseServerSetCookieHeader(it) }
            ?.find { it.name == "refresh_token" }
            ?.value?.let {
                tokenManager.saveRefreshToken(it)
            }

        return httpResponse.body<Response<SignInResponse>>()
    }
}