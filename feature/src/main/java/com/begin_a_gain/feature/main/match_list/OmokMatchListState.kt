package com.begin_a_gain.feature.main.match_list

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.match.MyMatchBoardItem
import com.begin_a_gain.model.type.match.MatchDoneStatus
import org.joda.time.DateTime

data class OmokMatchListState(
    override val loadingCount: Int = 0,
    val currentDate: DateTime = DateTime.now().withTimeAtStartOfDay(),
    val omokMatches: List<MyMatchBoardItem> = (1..8).map { MyMatchBoardItem(status = MatchDoneStatus.None) },
    val isMatchPasswordValid: Boolean = true
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface OmokMatchListSideEffect {
    data class SuccessToJoin(val matchId: Int): OmokMatchListSideEffect
}