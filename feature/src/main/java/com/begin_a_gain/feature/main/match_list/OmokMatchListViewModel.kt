package com.begin_a_gain.feature.main.match_list

import androidx.lifecycle.ViewModel
import org.joda.time.DateTime
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class OmokMatchListViewModel(

): ViewModel(), ContainerHost<OmokMatchListState, OmokMatchListSideEffect> {

    override val container: Container<OmokMatchListState, OmokMatchListSideEffect> = container(OmokMatchListState())

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