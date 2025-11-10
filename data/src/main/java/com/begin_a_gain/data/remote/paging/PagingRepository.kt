package com.begin_a_gain.data.remote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PagingRepository @Inject constructor(
    private val matchRepository: MatchRepository,
) {

    fun getMatchPageItems(
        pageSize: Int = 10
    ): Flow<PagingData<MatchInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize
            ),
            pagingSourceFactory = {
                MatchPagingSource(
                    matchRepository = matchRepository,
                    pageSize = pageSize
                )
            }
        ).flow
    }

}