package com.begin_a_gain.feature.main.match_list

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.util.common.DateTimeUtil.toString
import com.begin_a_gain.util.common.ODateTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OmokMatchListViewModel @Inject constructor(
    private val matchRepository: MatchRepository
): BaseViewModel<OmokMatchListState, OmokMatchListSideEffect>() {

    override val container: Container<OmokMatchListState, OmokMatchListSideEffect> = container(OmokMatchListState())

    init {
        viewModelScope.launch {
            fetchMatchCategory()
            fetchDailyMatchList(DateTime.now())
        }
    }

    fun addDate(day: Int) = intent {
        val date = state.currentDate.plusDays(day)
        reduce { state.copy(currentDate = state.currentDate.plusDays(day)) }
        fetchDailyMatchList(date)
    }

    fun setDate(date: DateTime) = intent {
        reduce {
            state.copy(currentDate = date)
        }
    }

    private suspend fun fetchMatchCategory() {
        matchRepository.getMatchCategoryList()
    }

    fun fetchDailyMatchList(date: DateTime) = intent {
        matchRepository.getMyDailyMatchList(date.toString(ODateTimeFormat.DateForNetwork))
            .onSuccess {
                intent {
                    reduce {
                        state.copy(
                            omokMatches = it
                        )
                    }
                }
            }
            .onFailure {

            }
    }
}