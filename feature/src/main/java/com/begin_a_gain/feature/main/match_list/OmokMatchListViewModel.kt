package com.begin_a_gain.feature.main.match_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OmokMatchListViewModel @Inject constructor(
    private val matchRepository: MatchRepository
): ViewModel(), ContainerHost<OmokMatchListState, OmokMatchListSideEffect> {

    override val container: Container<OmokMatchListState, OmokMatchListSideEffect> = container(OmokMatchListState())

    init {
        viewModelScope.launch {
            fetchMatchCategory()
        }
    }

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

    suspend fun fetchMatchCategory() {
        matchRepository.getMatchCategoryList()
    }
}