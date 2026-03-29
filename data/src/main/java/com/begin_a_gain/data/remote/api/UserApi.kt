package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.deletionSurvey
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.get
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.info
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.me
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.myPage
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.nickname
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.nicknameValidation
import com.begin_a_gain.data.remote.response.NicknameValidationResponse
import com.begin_a_gain.data.remote.response.UserInfoResponse
import com.begin_a_gain.data.remote.response.UserMyPageResponse
import com.begin_a_gain.data.remote.response.UserResponse
import com.begin_a_gain.domain.model.request.DeletionSurveyRequest
import com.begin_a_gain.domain.model.request.NicknameRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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

    suspend fun postNicknameValidation(nicknameRequest: NicknameRequest): Response<NicknameValidationResponse> {
        return client.post(ApiEndPoint.User.nicknameValidation()) {
            setBody(nicknameRequest)
        }.body<Response<NicknameValidationResponse>>()
    }

    suspend fun getUserInfo(): Response<UserInfoResponse> {
        return client.get(ApiEndPoint.User.info()).body<Response<UserInfoResponse>>()
    }

    suspend fun getMyPage(userId: Int): Response<UserMyPageResponse> {
        return client.get(ApiEndPoint.User.myPage(userId)).body()
    }

    suspend fun deleteAccount(): Response<Unit> {
        return client.delete(ApiEndPoint.User.me()).body()
    }

    suspend fun postDeletionSurvey(request: DeletionSurveyRequest): Response<Unit> {
        return client.post(ApiEndPoint.User.deletionSurvey()) {
            setBody(request)
        }.body()
    }

    suspend fun getUsers(nickname: String, cursor: String, pageSize: Int): Response<UserResponse> {
        return client.get(ApiEndPoint.User.get()) {
            parameter("nickname", nickname)
            parameter("cursor", cursor)
            parameter("size", pageSize)
        }.body()
    }
}