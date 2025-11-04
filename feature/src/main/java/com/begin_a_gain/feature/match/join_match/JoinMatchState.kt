package com.begin_a_gain.feature.match.join_match

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.match.MatchInfo

data class JoinMatchState(
    val isLoading: Boolean = false,
    val matchList: List<MatchInfo> = tmpMatchList,
    val categoryFilter: List<MatchCategoryItem> = listOf(),
    val availableMatchFilterSelected: Boolean = false,
    val selectedMatchCode: String = ""
)

sealed interface JoinMatchSideEffect {

}

val tmpMatchList = (1..10).map {
    MatchInfo(
        name = LoremIpsum(10).values.first(),
        owner = LoremIpsum(10).values.first().take(10)
    )
}