package com.begin_a_gain.feature.main.match_list

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.analytics.AnalyticsEvent
import com.begin_a_gain.core.analytics.AnalyticsHelper
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.model.match.MyMatchBoardItem
import com.begin_a_gain.domain.model.request.JoinMatchRequest
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.feature.match.join_match.JoinMatchSideEffect
import com.begin_a_gain.model.type.match.MatchDoneStatus
import com.begin_a_gain.util.common.DateTimeUtil.isToday
import com.begin_a_gain.util.common.DateTimeUtil.toString
import com.begin_a_gain.util.common.ODateTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class OmokMatchListViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    analyticsHelper: AnalyticsHelper
): BaseViewModel<OmokMatchListState, OmokMatchListSideEffect>(OmokMatchListState(), analyticsHelper) {

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

    private fun formatOmokMatchList(matchList: List<MyMatchBoardItem>): List<MyMatchBoardItem> {
        val maxCount = 8
        return if (matchList.size < maxCount) {
            matchList + (1..(maxCount - matchList.size)).map { MyMatchBoardItem(status = MatchDoneStatus.None) }
        } else if (matchList.size %2 == 1) {
            matchList + listOf(MyMatchBoardItem(status = MatchDoneStatus.None))
        } else matchList
    }

    fun completeOmok(matchId: Int) {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "complete_omok", screen = "match_list"))
        val state = container.stateFlow.value
        if (state.currentDate.isToday()) {
            withLoading {
                matchRepository.putMatchStatus(matchId)
                    .onSuccess {
                        logEvent(AnalyticsEvent.CompleteOmok(screen = "match_list"))
                        setDateAndFetchList(state.currentDate)
                    }
            }
        }
    }

    fun joinMatch(matchId: Int, password: String = "") {
        logEvent(AnalyticsEvent.ButtonClick(buttonName = "join_match", screen = "match_list"))
        withLoading {
            matchRepository.postJoinMatch(
                matchId = matchId,
                request = JoinMatchRequest(password)
            ).onSuccess { isJoined ->
                if (isJoined) {
                    logEvent(AnalyticsEvent.JoinMatch(matchId))
                    intent {
                        postSideEffect(OmokMatchListSideEffect.SuccessToJoin(matchId))
                    }
                } else {
                    if (password.isNotEmpty()) {
                        intent {
                            reduce { state.copy(isMatchPasswordValid = false) }
                        }
                    }
                }
            }.onFailure {
                if (password.isNotEmpty()) {
                    intent {
                        reduce { state.copy(isMatchPasswordValid = false) }
                    }
                }
            }
        }
    }
}