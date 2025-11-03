package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.info
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.nicknameValidation
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.nickname
import com.begin_a_gain.data.remote.response.NicknameResponse
import com.begin_a_gain.data.remote.response.UserInfoResponse
import com.begin_a_gain.domain.model.request.NicknameRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import javax.inject.Inject

class UserApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun putNickname(nicknameRequest: NicknameRequest): Response<Unit> {
        return client.put(ApiEndPoint.User.nickname()) {
            setBody(nicknameRequest)
        }.body<Response<Unit>>()
    }

    suspend fun postNicknameValidation(nicknameRequest: NicknameRequest): Response<NicknameResponse> {
        return client.post(ApiEndPoint.User.nicknameValidation()) {
            setBody(nicknameRequest)
        }.body<Response<NicknameResponse>>()
    }

    suspend fun getUserInfo(): Response<UserInfoResponse> {
        return client.get(ApiEndPoint.User.info()).body<Response<UserInfoResponse>>()
    }
}