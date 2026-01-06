package com.begin_a_gain.feature.match.join_match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.begin_a_gain.data.remote.paging.MatchPagingSource
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class JoinMatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel(), ContainerHost<JoinMatchState, JoinMatchSideEffect> {

    override val container: Container<JoinMatchState, JoinMatchSideEffect> =
        container(JoinMatchState())

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10
    )

    val matchPagingData = Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MatchPagingSource(matchRepository)
            }
        ).flow
        .cachedIn(viewModelScope)

    fun setCategoryFilter(indexList: List<MatchCategoryItem>) = intent {
        reduce { state.copy(categoryFilter = indexList) }
    }

    fun setAvailableMatchFilter() = intent {
        reduce { state.copy(availableMatchFilterSelected = !state.availableMatchFilterSelected) }
    }

    fun setCode(code: String) = blockingIntent {
        reduce { state.copy(selectedMatchCode = code) }
    }
}