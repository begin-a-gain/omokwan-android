package com.begin_a_gain.feature.main.match_list

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.match.MyMatchItem
import com.begin_a_gain.model.type.match.MatchStatus
import org.joda.time.DateTime

data class OmokMatchListState(
    override val loadingCount: Int = 0,
    val currentDate: DateTime = DateTime.now(),
    val omokMatches: List<MyMatchItem> = (1..8).map { MyMatchItem(status = MatchStatus.None) }
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface OmokMatchListSideEffect {

}