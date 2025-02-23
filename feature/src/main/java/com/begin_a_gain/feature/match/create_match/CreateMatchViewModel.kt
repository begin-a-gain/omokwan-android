package com.begin_a_gain.feature.match.create_match

import androidx.lifecycle.ViewModel
import com.begin_a_gain.feature.match.create_match.util.type.RepeatDayType
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CreateMatchViewModel(

) : ViewModel(), ContainerHost<CreateMatchState, CreateMatchSideEffect> {

    override val container: Container<CreateMatchState, CreateMatchSideEffect> =
        container(CreateMatchState())


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

    fun setCategory(selectedIndex: Int) = intent {
        reduce { state.copy(selectedCategoryIndex = selectedIndex) }
    }

    fun setAlarmOn(value: Boolean) = intent {
        reduce { state.copy(alarmOn = value) }
    }

    fun setPrivate(value: Boolean) = intent {
        reduce { state.copy(isPrivate = value) }
    }
}
