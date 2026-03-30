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
        val currentKey = params.key
        val pageSize = params.loadSize
        
        return try {
            val response = matchRepository.getMatchBoardPaging(matchId, currentKey ?: date, pageSize)
            
            val nextKey = if (response.hasPrevious) response.items.prevCursor else null
            val prevKey = if (response.hasNext) response.items.nextCursor else null

            LoadResult.Page(
                data = listOf(response.items),
                prevKey = if (prevKey == currentKey) null else prevKey,
                nextKey = if (nextKey == currentKey) null else nextKey
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
