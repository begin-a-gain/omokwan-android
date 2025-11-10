package com.begin_a_gain.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.begin_a_gain.data.remote.api.MatchApi
import com.begin_a_gain.data.remote.response.MatchItemResponse
import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MatchPagingSource(
    private val matchRepository: MatchRepository,
    private val pageSize: Int
) : PagingSource<Int, MatchInfo>() {

    val currentPage = MutableStateFlow(1)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MatchInfo> {
        val pageNumber = params.key ?: 1
        currentPage.value = pageNumber

        return try {
            val response = matchRepository.getAllMatchPagingItems(pageSize = pageSize)
            LoadResult.Page(
                data = response.items,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (response.hasNext) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MatchInfo>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
