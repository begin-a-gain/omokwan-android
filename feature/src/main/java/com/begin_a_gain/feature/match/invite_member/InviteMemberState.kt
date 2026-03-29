package com.begin_a_gain.feature.match.invite_member

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.user.User

data class InviteMemberState(
    override val loadingCount: Int = 0,
    val maxParticipants: Int = 5,
    val currentMembers: List<Int> = emptyList(),
    val newMembers: List<User> = emptyList(),
    val searchQuery: String = ""
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface InviteMemberSideEffect {
    object ExceedMaximum : InviteMemberSideEffect
    object InvitationSuccess : InviteMemberSideEffect
}