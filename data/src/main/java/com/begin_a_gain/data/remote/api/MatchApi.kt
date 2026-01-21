package com.begin_a_gain.data.remote.api

import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.all
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.board
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.categories
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.participants
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Match.settings
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.create
import com.begin_a_gain.data.remote.constant.ApiEndPoint.User.get
import com.begin_a_gain.data.remote.response.match.CreateMatchResponse
import com.begin_a_gain.data.remote.response.match.JoinMatchResponse
import com.begin_a_gain.data.remote.response.match.MatchBoardResponse
import com.begin_a_gain.data.remote.response.match.MatchCategoryItemResponse
import com.begin_a_gain.data.remote.response.match.MatchItemResponse
import com.begin_a_gain.data.remote.response.match.MatchListResponse
import com.begin_a_gain.data.remote.response.match.MatchSettingsResponse
import com.begin_a_gain.data.remote.response.match.MyDailyMatchResponse
import com.begin_a_gain.data.remote.response.match.ParticipantsResponse
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import com.begin_a_gain.domain.model.request.JoinMatchRequest
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

    suspend fun postMatchParticipants(matchId: Int, request: JoinMatchRequest): Response<JoinMatchResponse> {
        return client.post(ApiEndPoint.Match.participants(matchId)) {
            setBody(request)
        }.body<Response<JoinMatchResponse>>()
    }

    suspend fun getMatchBoard(matchId: Int): Response<MatchBoardResponse> {
        return client.get(ApiEndPoint.Match.board(matchId)) {
            parameter("date", "2026-01-21")
            parameter("size", 10)
        }.body()
    }

    suspend fun getParticipants(matchId: Int): Response<ParticipantsResponse> {
        return client.get(ApiEndPoint.Match.participants(matchId)).body()
    }

    suspend fun getMatchSettings(matchId: Int): Response<MatchSettingsResponse> {
        return client.get(ApiEndPoint.Match.settings(matchId)).body()
    }
}