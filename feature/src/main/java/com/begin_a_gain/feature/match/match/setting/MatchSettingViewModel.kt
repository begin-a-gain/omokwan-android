package com.begin_a_gain.feature.match.match.setting

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchSettingViewModel @Inject constructor(
private val matchRepository: MatchRepository
) : BaseViewModel<MatchSettingState, Nothing>(MatchSettingState()) {

    fun initialize(matchId: Int) {
        viewModelScope.withLoading {
            matchRepository.getMatchSettings(matchId)
                .onSuccess {
                    intent {
                        reduce {
                            state.copy(
                                title = it.name,
                                daysInProgress = it.ongoingDays,
                                matchCode = it.matchCode,
                                selectedDay = it.repeatDayTypes.map { type ->
                                    type == 1
                                },
                                maxParticipantsCount = it.maxParticipants,
                                selectedCategory = it.category,
                                isPrivate = !it.isPublic,
                                password = it.password
                            )
                        }
                    }
                }
        }
    }
}