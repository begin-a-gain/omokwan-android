package com.begin_a_gain.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.begin_a_gain.domain.model.match.MatchBoard
import com.begin_a_gain.domain.repository.MatchRepository

class MatchBoardPagingSource(
    private val matchRepository: MatchRepository,
    val matchId: Int,
    val date: String
) : PagingSource<String, MatchBoard>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MatchBoard> {
        val pageSize = params.loadSize
        return try {
            val response = matchRepository.getMatchBoardPaging(matchId, date, pageSize)
            LoadResult.Page(
                data = listOf(response.items),
                prevKey = if (response.hasNext) response.items.nextCursor else null,
                nextKey = if (response.hasPrevious) response.items.prevCursor else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, MatchBoard>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { anchorPage ->
                anchorPage.prevKey ?: anchorPage.nextKey
            }
        }
    }
}