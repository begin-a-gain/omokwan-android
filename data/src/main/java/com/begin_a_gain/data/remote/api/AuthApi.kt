package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.kakaoSignIn
import com.begin_a_gain.data.remote.response.SignInResponse
import com.begin_a_gain.domain.model.request.SignInRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun kakaoSignIn(signInRequest: SignInRequest): Response<SignInResponse> {
        return client.post(ApiEndPoint.Auth.kakaoSignIn()) {
            setBody(signInRequest)
        }.body<Response<SignInResponse>>()
    }
}