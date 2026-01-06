package com.begin_a_gain.feature.match.join_match

import com.begin_a_gain.domain.model.match.MatchCategoryItem

data class JoinMatchState(
    val isLoading: Boolean = false,
    val keyword: String = "",
    val categoryFilter: List<MatchCategoryItem> = listOf(),
    val availableMatchFilterSelected: Boolean = false,
    val selectedMatchCode: String = ""
)