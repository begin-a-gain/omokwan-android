package com.begin_a_gain.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.begin_a_gain.domain.model.user.User
import com.begin_a_gain.domain.repository.UserRepository

class AllUsersPagingSource(
    private val userRepository: UserRepository,
    private val nickname: String
) : PagingSource<String, User>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, User> {
        val cursor = params.key ?: ""
        val pageSize = params.loadSize

        return try {
            val result = userRepository.getUsersPaging(nickname, cursor, pageSize)
            
            result.fold(
                onSuccess = { response ->
                    LoadResult.Page(
                        data = response.users,
                        prevKey = null,
                        nextKey = if (response.hasNext) response.nextCursor else null
                    )
                },
                onFailure = {
                    LoadResult.Error(it)
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, User>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { anchorPage ->
                anchorPage.prevKey ?: anchorPage.nextKey
            }
        }
    }
}
