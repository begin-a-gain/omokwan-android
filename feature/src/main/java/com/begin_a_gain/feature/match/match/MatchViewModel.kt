package com.begin_a_gain.feature.match.match

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.ParticipantInfo
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : BaseViewModel<MatchState, Nothing>(MatchState()) {

    private val currentMatchId = MutableStateFlow(-1)

    fun initialize(matchId: Int) {
        currentMatchId.value = matchId
        viewModelScope.withLoading {
            val board = matchRepository.getMatchBoard(matchId)
                .getOrDefault(null)

            val participants: List<ParticipantInfo> = matchRepository.getParticipants(matchId)
                .getOrDefault(emptyList())

            intent {
                val participantMap = participants.associateBy { it.id }
                val combinedParticipants = board?.users?.map { user ->
                    val info = participantMap[user.userId]
                    ParticipantInfo(
                        id = user.userId,
                        name = user.nickname,
                        combo = info?.combo ?: 0,
                        omok = info?.omok ?: 0,
                        days = info?.days ?: 0,
                        isHost = user.isHost
                    )
                } ?: participants

                reduce { state.copy(participants = combinedParticipants) }
            }
        }
    }
}