package com.begin_a_gain.feature.match.join_match

import androidx.lifecycle.ViewModel
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

    fun setCategoryFilter(indexList: List<Int>) = intent {
        reduce { state.copy(categoryFilter = indexList) }
    }
}