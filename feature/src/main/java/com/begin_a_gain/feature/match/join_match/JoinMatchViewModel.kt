package com.begin_a_gain.feature.match.join_match

import androidx.lifecycle.ViewModel
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class JoinMatchViewModel @Inject constructor(

) : ViewModel(), ContainerHost<JoinMatchState, JoinMatchSideEffect> {

    override val container: Container<JoinMatchState, JoinMatchSideEffect> =
        container(JoinMatchState())

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