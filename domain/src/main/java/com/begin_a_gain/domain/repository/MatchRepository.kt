package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.BoardPageResult
import com.begin_a_gain.domain.model.MemberInfo
import com.begin_a_gain.domain.model.PageResult
import com.begin_a_gain.domain.model.match.MatchBoard
import com.begin_a_gain.domain.model.match.MatchBoardInitialInfo
import com.begin_a_gain.domain.model.match.MatchBoardUser
import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.model.match.MatchSettings
import com.begin_a_gain.domain.model.match.MyMatchItem
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import com.begin_a_gain.domain.model.request.JoinMatchRequest

interface MatchRepository {
    suspend fun getMatchCategoryList(): Result<Boolean>
    suspend fun postCreateMatch(request: CreateMatchRequest): Result<Int>
    suspend fun getMyDailyMatchList(date: String): Result<List<MyMatchItem>>
    suspend fun getAllMatchPagingItems(
        pageNumber: Int,
        pageSize: Int,
        category: List<Int>,
        joinable: Boolean,
        keyword: String
    ): PageResult<MatchInfo>
    suspend fun postJoinMatch(matchId: Int, request: JoinMatchRequest): Result<Boolean>
    suspend fun getParticipants(matchId: Int): Result<List<MemberInfo>>
    suspend fun getMatchBoardPaging(matchId: Int, date: String, pageSize: Int): BoardPageResult<MatchBoard>
    suspend fun getMatchBoardInitialData(matchId: Int, date: String): Result<MatchBoardInitialInfo>
    suspend fun getMatchSettings(matchId: Int): Result<MatchSettings>
    suspend fun postChangeHost(matchId: Int, newHostId: Int): Result<Boolean>
    suspend fun deleteMe(matchId: Int): Result<Boolean>
    suspend fun postKickUser(matchId: Int, userId: Int): Result<Boolean>
    suspend fun putMatchStatus(matchId: Int): Result<Boolean>
}