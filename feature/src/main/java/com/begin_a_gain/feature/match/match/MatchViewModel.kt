package com.begin_a_gain.feature.match.match

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.insertSeparators
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.data.remote.paging.MatchBoardPagingSource
import com.begin_a_gain.domain.model.MemberInfo
import com.begin_a_gain.domain.model.match.MatchBoardInitialInfo
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.util.common.DateTimeUtil.toString
import com.begin_a_gain.util.common.ODateTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
) : BaseViewModel<MatchState, MatchSideEffect>(MatchState()) {

    private val currentMatchId = MutableStateFlow(-1)

    private val refreshPagingTrigger = container.stateFlow
        .map { it.todayDone }
        .distinctUntilChanged()
        .filter { it }

    private val boardPager = Pager(
        config = PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
            prefetchDistance = 30
        ),
        pagingSourceFactory = {
            MatchBoardPagingSource(
                matchRepository,
                matchId = currentMatchId.value,
                date = DateTime.now().toString(ODateTimeFormat.DateForNetwork)
            )
        }
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val calendarItems = refreshPagingTrigger
        .onStart { emit(true) }
        .flatMapLatest {
            boardPager.flow
        }
        .map { pagingData ->
            pagingData.flatMap { board ->
                board.dates.reversed().map { CalendarItem.Day(it) }
            }.insertSeparators { before, after ->
                val beforeDate =
                    (before as? CalendarItem.Day)?.data?.date?.let { DateTime.parse(it) }
                val afterDate = (after as? CalendarItem.Day)?.data?.date?.let { DateTime.parse(it) }

                if (afterDate != null && (beforeDate == null || beforeDate.monthOfYear != afterDate.monthOfYear)) {
                    CalendarItem.Header(afterDate.toString(ODateTimeFormat.CalendarHeader))
                } else {
                    null
                }
            }
        }
        .cachedIn(viewModelScope)

    fun initialize(
        isInitial: Boolean,
        matchId: Int,
        saveMatchInfo: (Boolean, List<MemberInfo>) -> Unit
    ) {
        currentMatchId.value = matchId
        withLoading {
            val boardInitialInfo = getTodayInfo(matchId)
            val participants: List<MemberInfo> = matchRepository.getParticipants(matchId)
                .getOrDefault(emptyList())

            intent {
                var amIHost = false
                val participantMap = participants.associateBy { it.id }
                val combinedParticipants = boardInitialInfo?.users?.mapIndexed { index, user ->
                    val info = participantMap[user.userId]
                    if (user.isHost) {
                        amIHost = index == 0
                    }
                    MemberInfo(
                        id = user.userId,
                        name = user.nickname,
                        combo = info?.combo ?: 0,
                        omok = info?.omok ?: 0,
                        days = info?.days ?: 0,
                        isHost = user.isHost
                    )
                } ?: participants
                saveMatchInfo(amIHost, combinedParticipants)
                reduce {
                    state.copy(
                        participants = combinedParticipants,
                        amIHost = amIHost,
                        todayDone = boardInitialInfo?.isTodayMatchCompleted ?: false,
                        maxParticipants = boardInitialInfo?.maxParticipants ?: 5
                    )
                }
                if (isInitial) {
                    postSideEffect(MatchSideEffect.ShowInitialToast)
                }
            }
        }
    }

    fun completeOmok() {
        withLoading {
            matchRepository.putMatchStatus(currentMatchId.value)
                .onSuccess {
                    val isComboToday = getTodayInfo(currentMatchId.value)?.isTodayCombo?: false
                    intent {
                        reduce { state.copy(todayDone = true) }
                        if (isComboToday) {
                            val myCombo = (container.stateFlow.value.participants.firstOrNull()?.combo)?: 0
                            postSideEffect(MatchSideEffect.ShowCombo(myCombo + 1))
                        } else {
                            postSideEffect(MatchSideEffect.SuccessToCompleteOmok)
                        }
                    }
                }
        }
    }

    private suspend fun getTodayInfo(
        matchId: Int,
    ): MatchBoardInitialInfo? {
        val loadDate =
            DateTime.now().withTimeAtStartOfDay().toString(ODateTimeFormat.DateForNetwork)
        return matchRepository.getMatchBoardInitialData(matchId, loadDate)
            .getOrDefault(null)
    }

    fun kickMember(member: MemberInfo) {
        withLoading {
            matchRepository.postKickUser(
                matchId = currentMatchId.value,
                userId = member.id
            ).onSuccess {
                intent {
                    postSideEffect(MatchSideEffect.SuccessToKickMember(member.name))
                }
            }
        }
    }
}