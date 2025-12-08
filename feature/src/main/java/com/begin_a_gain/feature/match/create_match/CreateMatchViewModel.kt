package com.begin_a_gain.feature.match.create_match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType
import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType.Companion.parseToMatchDayType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.joda.time.LocalTime
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CreateMatchViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val matchRepository: MatchRepository
) : BaseViewModel<CreateMatchState, CreateMatchSideEffect>() {

    override val container: Container<CreateMatchState, CreateMatchSideEffect> =
        container(CreateMatchState())

    init {
        intent {
            reduce {
                state.copy(
                    categoryList = localRepository.getCategoryList(),
                    alarmHour = LocalTime.now().hourOfDay,
                    alarmMin = LocalTime.now().minuteOfHour
                )
            }
        }
    }

    val isMatchCreatable: Flow<Boolean> = container.stateFlow
        .map { state ->
            state.title.isNotBlank() && state.selectedCategory != null
        }
        .distinctUntilChanged()

    fun setMatchTitle(value: String) = blockingIntent {
        reduce { state.copy(title = value) }
    }

    fun setRepeatDayType(value: RepeatDayType) = intent {
        reduce { state.copy(selectedRepeatDayType = value) }
    }

    fun setRepeatDay(selectedIndex: Int) = intent {
        reduce {
            state.copy(
                selectedDay = state.selectedDay.mapIndexed { index, value ->
                    if (index == selectedIndex) !value else value
                }
            )
        }
    }

    fun setMaximumParticipants(value: Int) = intent {
        reduce { state.copy(maxParticipantsCount = value) }
    }

    fun setCategory(selectedItem: MatchCategoryItem?) = intent {
        reduce {
            state.copy(
                selectedCategory = if (selectedItem == null) null
                else if (state.selectedCategory == selectedItem) null else selectedItem
            )
        }
    }

    fun setAlarmOn(value: Boolean, hour: Int? = null, min: Int? = null) = intent {
        reduce { state.copy(alarmOn = value, alarmHour = hour ?: 0, alarmMin = min ?: 0) }
    }

    fun setPrivate(value: Boolean, code: String? = null) = intent {
        reduce { state.copy(isPrivate = value, code = code ?: "") }
    }

    fun createMatch() {
        viewModelScope.withLoading {
            val state = container.stateFlow.value
            matchRepository.postCreateMatch(
                request = CreateMatchRequest(
                    name = state.title,
                    maxParticipants = state.maxParticipantsCount,
                    categoryCode = state.selectedCategory?.code ?: "",
                    dayType = state.selectedRepeatDayType.parseToMatchDayType(state.selectedDay),
                    password = if (state.isPrivate) state.code else null,
                    isPublic = !state.isPrivate
                )
            ).onSuccess {
                if (it != -1) {
                    intent {
                        postSideEffect(CreateMatchSideEffect.CreateSuccess)
                    }
                }
            }.onFailure {
                Log.d("junyoung", "failed")
            }
        }
    }
}
