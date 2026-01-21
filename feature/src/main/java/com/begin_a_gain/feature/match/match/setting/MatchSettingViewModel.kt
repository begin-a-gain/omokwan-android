package com.begin_a_gain.feature.match.match.setting

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.match.MatchCategoryItem
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
                                initialSettings = MatchSettingsItem(
                                    title = it.name,
                                    maxParticipantsCount = it.maxParticipants,
                                    selectedCategory = it.category,
                                    isPrivate = !it.isPublic,
                                    password = it.password
                                ),
                                currentSettings = MatchSettingsItem(
                                    title = it.name,
                                    maxParticipantsCount = it.maxParticipants,
                                    selectedCategory = it.category,
                                    isPrivate = !it.isPublic,
                                    password = it.password
                                ),
                                daysInProgress = it.ongoingDays,
                                matchCode = it.matchCode,
                                selectedDay = it.repeatDayTypes.map { type ->
                                    type == 1
                                }
                            )
                        }
                    }
                }
        }
    }

    fun setTitle(title: String) = intent {
        reduce {
            state.copy(
                currentSettings = state.currentSettings.copy(title = title)
            )
        }
    }

    fun setMaxParticipantsCount(count: Int) = intent {
        reduce {
            state.copy(
                currentSettings = state.currentSettings.copy(maxParticipantsCount = count)
            )
        }
    }

    fun setCategory(selectedItem: MatchCategoryItem?) = intent {
        reduce {
            state.copy(
                currentSettings = state.currentSettings.copy(selectedCategory = selectedItem)
            )
        }
    }

    fun setPrivate(value: Boolean, code: String? = null) = intent {
        reduce {
            state.copy(
                currentSettings = state.currentSettings.copy(
                    isPrivate = value,
                    password = code ?: ""
                )
            )
        }
    }
}