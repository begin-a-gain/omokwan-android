package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.categories
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.create
import com.begin_a_gain.data.remote.response.CreateMatchResponse
import com.begin_a_gain.data.remote.response.MatchCategoryItemResponse
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class MatchApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getMatchCategoryList(): Response<List<MatchCategoryItemResponse>> {
        return client.get(ApiEndPoint.Match.categories()).body()
    }

    suspend fun postMatch(createMatchRequest: CreateMatchRequest): Response<CreateMatchResponse> {
        return client.post(ApiEndPoint.Match.create()) {
            setBody(createMatchRequest)
        }.body<Response<CreateMatchResponse>>()
    }
}