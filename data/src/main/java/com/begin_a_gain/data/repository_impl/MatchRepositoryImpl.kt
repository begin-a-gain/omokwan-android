package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.remote.api.MatchApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.match.MatchItem
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.model.type.match.MatchStatus
import javax.inject.Inject

class MatchRepositoryImpl @Inject internal constructor(
    private val matchApi: MatchApi,
    private val localRepository: LocalRepository
): MatchRepository {

    override suspend fun getMatchCategoryList(): Result<Boolean> {
        return callApi(
            call = {
                matchApi.getMatchCategoryList()
            },
            handleResponse = { response ->
                response?.let {
                    if (it.isEmpty()) false
                    else {
                        val categoryList = it.map { category ->
                            MatchCategoryItem(
                                code = category.code,
                                name = category.category,
                                emoji = category.emoji
                            )
                        }
                        localRepository.saveCategoryList(categoryList)
                        true
                    }
                }
            }
        )
    }

    override suspend fun postCreateMatch(request: CreateMatchRequest): Result<Int> {
        return callApi(
            call = {
                matchApi.postMatch(request)
            },
            handleResponse = {
                it?.matchId ?: -1
            }
        )
    }

    override suspend fun getMyDailyMatchList(date: String): Result<List<MatchItem>> {
        return callApi(
            call = {
                matchApi.getMatch(date)
            },
            handleResponse = { response ->
                response?.map {
                    MatchItem(
                        matchId = it.matchId,
                        name = it.name,
                        ongoingDays = it.ongoingDays,
                        participants = it.participants,
                        maxParticipants = it.maxParticipants,
                        public = it.public,
                        status = MatchStatus.getMatchStatus(
                            date = date,
                            isCompleted = it.completed
                        )
                    )
                }?: emptyList()
            }
        )
    }
}