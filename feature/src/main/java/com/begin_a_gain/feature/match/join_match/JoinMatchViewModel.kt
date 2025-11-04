package com.begin_a_gain.feature.match.join_match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class JoinMatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel(), ContainerHost<JoinMatchState, JoinMatchSideEffect> {

    override val container: Container<JoinMatchState, JoinMatchSideEffect> =
        container(JoinMatchState())

    init {
        fetchMatchList()
    }

    fun setCategoryFilter(indexList: List<MatchCategoryItem>) = intent {
        reduce { state.copy(categoryFilter = indexList) }
    }

    fun setAvailableMatchFilter() = intent {
        reduce { state.copy(availableMatchFilterSelected = !state.availableMatchFilterSelected) }
    }

    fun setCode(code: String) = blockingIntent {
        reduce { state.copy(selectedMatchCode = code) }
    }

    private fun fetchMatchList() {
        viewModelScope.launch {
            matchRepository.getAllMatchItems()
                .onSuccess {
                    Log.d("junyoung", "list : $it")
                    intent { reduce { state.copy(matchList = it) } }
                }
                .onFailure {
                    Log.d("junyoung", "onFailure : $it")
                }
        }
    }
}