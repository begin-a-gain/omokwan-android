package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.model.match.MyMatchItem
import com.begin_a_gain.domain.model.request.CreateMatchRequest

interface MatchRepository {
    suspend fun getMatchCategoryList(): Result<Boolean>
    suspend fun postCreateMatch(request: CreateMatchRequest): Result<Int>
    suspend fun getMyDailyMatchList(date: String): Result<List<MyMatchItem>>
    suspend fun getAllMatchItems(): Result<List<MatchInfo>>
}