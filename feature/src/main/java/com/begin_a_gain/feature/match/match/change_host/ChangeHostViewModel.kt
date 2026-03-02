package com.begin_a_gain.feature.match.match.change_host

import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.MemberInfo
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChangeHostViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : BaseViewModel<ChangeHostState, ChangeHostSideEffect>(ChangeHostState()) {

    private var currentMatchId = MutableStateFlow(-1)

    fun initialize(matchId: Int, participants: List<MemberInfo>) = intent {
        currentMatchId.value = matchId
        reduce {
            state.copy(
                participants = participants.filter { !it.isHost }
            )
        }
    }

    fun setSelectedIndex(index: Int) = intent {
        reduce { state.copy(selectedIndex = index) }
    }

    fun changeHost() {
        withLoading {
            val state = container.stateFlow.value
            val newHost = state.participants[state.selectedIndex]
            matchRepository.postChangeHost(
                matchId = currentMatchId.value,
                newHostId = newHost.id
            ).onSuccess {
                intent {
                    postSideEffect(ChangeHostSideEffect.ChangeSuccess(newHost.name))
                }
            }
        }
    }
}