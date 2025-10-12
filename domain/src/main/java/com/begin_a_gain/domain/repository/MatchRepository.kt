package com.begin_a_gain.domain.repository

interface MatchRepository {
    suspend fun getMatchCategoryList(): Result<Boolean>
}