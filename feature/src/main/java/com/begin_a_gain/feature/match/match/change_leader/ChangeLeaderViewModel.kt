package com.begin_a_gain.feature.match.match.change_leader

import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.ParticipantInfo
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChangeLeaderViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : BaseViewModel<ChangeLeaderState, Nothing>(ChangeLeaderState()) {

    private var currentMatchId = MutableStateFlow(-1)

    fun initialize(matchId: Int, participants: List<ParticipantInfo>) = intent {
        currentMatchId.value = matchId
        reduce {
            state.copy(
                participants = participants.filter { !it.isHost }
            )
        }
    }
}