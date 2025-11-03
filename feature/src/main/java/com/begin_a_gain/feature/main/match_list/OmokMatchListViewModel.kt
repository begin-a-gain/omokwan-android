package com.begin_a_gain.feature.main.match_list

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.match.MatchItem
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.model.type.match.MatchStatus
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

    fun addDateAndFetchList(day: Int) = intent {
        val date = state.currentDate.plusDays(day)
        reduce { state.copy(currentDate = state.currentDate.plusDays(day)) }
        fetchDailyMatchList(date)
    }

    fun setDateAndFetchList(date: DateTime) = intent {
        reduce { state.copy(currentDate = date) }
        fetchDailyMatchList(date)
    }

    private suspend fun fetchMatchCategory() {
        matchRepository.getMatchCategoryList()
    }

    private fun fetchDailyMatchList(date: DateTime) = intent {
        matchRepository.getMyDailyMatchList(date.toString(ODateTimeFormat.DateForNetwork))
            .onSuccess { matchList ->
                intent {
                    reduce {
                        state.copy(omokMatches = formatOmokMatchList(matchList))
                    }
                }
            }
            .onFailure {
                intent {
                    reduce {
                        state.copy(omokMatches = formatOmokMatchList(emptyList()))
                    }
                }
            }
    }

    private fun formatOmokMatchList(matchList: List<MatchItem>): List<MatchItem> {
        val maxCount = 8
        return if (matchList.size < maxCount) {
            matchList + (1..(maxCount - matchList.size)).map { MatchItem(status = MatchStatus.None) }
        } else if (matchList.size %2 == 1) {
            matchList + listOf(MatchItem(status = MatchStatus.None))
        } else matchList
    }
}