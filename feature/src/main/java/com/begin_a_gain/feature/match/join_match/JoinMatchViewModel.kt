package com.begin_a_gain.feature.match.join_match

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.data.remote.paging.MatchPagingSource
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.model.request.JoinMatchRequest
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.blockingIntent
import javax.inject.Inject

@HiltViewModel
class JoinMatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : BaseViewModel<JoinMatchState, JoinMatchSideEffect>(JoinMatchState()) {

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10
    )

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val matchPagingData = container.stateFlow
        .map { Triple(it.keyword, it.availableMatchFilterSelected, it.categoryFilter) }
        .distinctUntilChanged()
        .debounce(500L)
        .flatMapLatest { (query, joinable, category) ->
            Pager(
                config = pagingConfig,
                pagingSourceFactory = {
                    MatchPagingSource(
                        matchRepository,
                        category = category.map { it.code.toInt() },
                        joinable = joinable,
                        keyword = query
                    )
                }
            ).flow
        }.cachedIn(viewModelScope)

    fun setSkeletonLoading(value: Boolean) = intent {
        reduce { state.copy(loadingCount = if (value) 1 else 0) }
    }

    fun setSelectedMatch(match: MatchInfo?) = intent {
        reduce {
            state.copy(
                selectedMatch = match,
                isMatchPasswordValid = true
            )
        }
    }

    fun setKeyword(value: String) = blockingIntent {
        reduce { state.copy(keyword = value) }
    }

    fun setCategoryFilter(indexList: List<MatchCategoryItem>) = intent {
        reduce { state.copy(categoryFilter = indexList) }
    }

    fun setAvailableMatchFilter() = intent {
        reduce { state.copy(availableMatchFilterSelected = !state.availableMatchFilterSelected) }
    }

    fun joinMatch(matchId: Int, password: String = "") = intent {
        reduce { state.copy(isJoining = true) }
        matchRepository.postJoinMatch(
            matchId = matchId,
            request = JoinMatchRequest(password)
        ).onSuccess { isJoined ->
            if (isJoined) {
                intent {
                    postSideEffect(JoinMatchSideEffect.JoinSuccess(matchId, state.selectedMatch?.name?: ""))
                }
            } else {
                if (password.isNotEmpty()) {
                    intent {
                        reduce { state.copy(isMatchPasswordValid = false) }
                    }
                }
            }
            reduce { state.copy(isJoining = false) }
        }.onFailure {
            if (password.isNotEmpty()) {
                intent {
                    reduce { state.copy(isMatchPasswordValid = false) }
                }
            }
            reduce { state.copy(isJoining = false) }
        }
    }
}