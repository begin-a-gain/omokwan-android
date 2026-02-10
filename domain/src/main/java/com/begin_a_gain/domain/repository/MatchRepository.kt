package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.ParticipantInfo
import com.begin_a_gain.domain.model.PageResult
import com.begin_a_gain.domain.model.match.MatchBoard
import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.model.match.MyMatchBoardItem
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import com.begin_a_gain.domain.model.request.JoinMatchRequest

interface MatchRepository {
    suspend fun getMatchCategoryList(): Result<Boolean>
    suspend fun postCreateMatch(request: CreateMatchRequest): Result<Int>
    suspend fun getMyDailyMatchList(date: String): Result<List<MyMatchBoardItem>>
    suspend fun getAllMatchPagingItems(
        pageNumber: Int,
        pageSize: Int,
        category: List<Int>,
        joinable: Boolean,
        keyword: String
    ): PageResult<MatchInfo>
    suspend fun postJoinMatch(matchId: Int, request: JoinMatchRequest): Result<Boolean>
    suspend fun getParticipants(matchId: Int): Result<List<ParticipantInfo>>
    suspend fun getMatchBoard(matchId: Int): Result<MatchBoard>
}