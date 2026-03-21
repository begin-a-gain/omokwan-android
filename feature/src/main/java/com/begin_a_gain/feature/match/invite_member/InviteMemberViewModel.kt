package com.begin_a_gain.feature.match.invite_member

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.data.remote.paging.AllUsersPagingSource
import com.begin_a_gain.domain.model.user.User
import com.begin_a_gain.domain.repository.UserRepository
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
import org.orbitmvi.orbit.blockingIntent
import javax.inject.Inject

@HiltViewModel
class InviteMemberViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<InviteMemberState, InviteMemberSideEffect>(InviteMemberState()) {

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

    fun initialize(maxParticipants: Int, currentMembers: List<Int>) = intent {
        reduce {
            state.copy(
                maxParticipants = maxParticipants,
                currentMembers = currentMembers
            )
        }
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
}
