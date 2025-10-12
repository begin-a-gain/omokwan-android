package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.remote.api.MatchApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.MatchRepository
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
}