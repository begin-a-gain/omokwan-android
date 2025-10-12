package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.request.CreateMatchRequest

interface MatchRepository {
    suspend fun getMatchCategoryList(): Result<Boolean>
    suspend fun postCreateMatch(request: CreateMatchRequest): Result<Int>
}