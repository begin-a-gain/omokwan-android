package com.begin_a_gain.feature.match.invite_member

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.data.remote.paging.AllUsersPagingSource
import com.begin_a_gain.domain.model.MemberInfo
import com.begin_a_gain.domain.model.match.MatchBoardInitialInfo
import com.begin_a_gain.domain.model.user.User
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.domain.repository.UserRepository
import com.begin_a_gain.util.common.DateTimeUtil.toString
import com.begin_a_gain.util.common.ODateTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import org.orbitmvi.orbit.blockingIntent
import javax.inject.Inject

@HiltViewModel
class InviteMemberViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository
) : BaseViewModel<InviteMemberState, InviteMemberSideEffect>(InviteMemberState()) {

    private val _currentMatchId = MutableStateFlow(-1)
    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val usersPagingData: Flow<PagingData<User>> = combine(
        _searchQuery.debounce(300L).distinctUntilChanged(),
        container.stateFlow.map { it.currentMembers }.distinctUntilChanged()
    ) { query, currentMembers ->
        query to currentMembers
    }.flatMapLatest { (query, currentMembers) ->
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AllUsersPagingSource(userRepository, query)
            }
        ).flow.map { pagingData ->
            pagingData.filter { user ->
                !currentMembers.contains(user.userId)
            }
        }
    }.cachedIn(viewModelScope)

    fun initialize(matchId: Int) = withLoading {
        _currentMatchId.value = matchId
        val boardInitialInfo = getTodayInfo(matchId)
        matchRepository.getParticipants(matchId)
            .onSuccess { participants ->
                intent {
                    reduce {
                        state.copy(
                            maxParticipants = boardInitialInfo?.maxParticipants?: 5,
                            currentMembers = participants.map { it.id }
                        )
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

    fun onSearchQueryChanged(query: String) = blockingIntent {
        _searchQuery.value = query
        reduce { state.copy(searchQuery = query) }
    }

    fun selectNewMember(user: User) = intent {
        val currentNewMembers = state.newMembers
        if (currentNewMembers.any { it.userId == user.userId }) {
            reduce { state.copy(newMembers = currentNewMembers - user) }
        } else {
            val totalCount = state.currentMembers.size + currentNewMembers.size
            if (totalCount < state.maxParticipants) {
                reduce { state.copy(newMembers = currentNewMembers + user) }
            } else {
                postSideEffect(InviteMemberSideEffect.ExceedMaximum)
            }
        }
    }

    fun inviteMembers() {
        val state = container.stateFlow.value
        withLoading {
            matchRepository.postInvitees(
                matchId = _currentMatchId.value,
                invitees = state.newMembers.map { it.userId }
            ).onSuccess {
                intent {
                    postSideEffect(InviteMemberSideEffect.InvitationSuccess)
                }
            }
        }
    }
}
