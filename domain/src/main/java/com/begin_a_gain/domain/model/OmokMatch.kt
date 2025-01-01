package com.begin_a_gain.domain.model

import com.begin_a_gain.domain.enum.MatchStatus

data class OmokMatch(
    val id: String = "",
    val title: String = "",
    val isLocked: Boolean = true,
    val date: Int = 0,
    val member: Int = 0,
    val status: MatchStatus = MatchStatus.None,
)