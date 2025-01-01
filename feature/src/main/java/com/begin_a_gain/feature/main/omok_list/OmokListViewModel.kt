package com.begin_a_gain.feature.main.omok_list

import androidx.lifecycle.ViewModel
import org.joda.time.DateTime
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class OmokListViewModel(

): ViewModel(), ContainerHost<OmokListState, OmokListSideEffect> {

    override val container: Container<OmokListState, OmokListSideEffect> = container(OmokListState())

    fun addDate(day: Int) = intent {
        reduce {
            state.copy(currentDate = state.currentDate.plusDays(day))
        }
    }

    fun setDate(date: DateTime) = intent {
        reduce {
            state.copy(currentDate = date)
        }
    }
}