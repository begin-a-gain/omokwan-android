package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.all
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.categories
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.create
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.get
import com.begin_a_gain.data.remote.response.CreateMatchResponse
import com.begin_a_gain.data.remote.response.MatchCategoryItemResponse
import com.begin_a_gain.data.remote.response.MatchListResponse
import com.begin_a_gain.data.remote.response.MyDailyMatchResponse
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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

    suspend fun getMyMatch(date: String): Response<List<MyDailyMatchResponse>> {
        return client.get(ApiEndPoint.Match.get()) {
            parameter("date", date)
        }.body()
    }

    suspend fun getAllMatches(
    ): Response<MatchListResponse> {
        return client.get(ApiEndPoint.Match.all()) {}.body()
    }

    suspend fun getAllMatchesPaging(
        pageNumber: Int = 1,
        pageSize: Int = 10,
        category: List<Int>,
        joinable: Boolean,
        keyword: String
    ): Response<MatchListResponse> {
        return client.get(ApiEndPoint.Match.all()) {
            parameter("pageNumber", pageNumber)
            parameter("pageSize", pageSize)
            if (category.isNotEmpty()) {
                parameter("category", category.joinToString(","))
            }
            if (joinable) {
                parameter("joinable", true)
            }
            parameter("keyword", keyword)
        }.body()
    }
}