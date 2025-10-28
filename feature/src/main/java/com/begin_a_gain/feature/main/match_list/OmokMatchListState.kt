package com.begin_a_gain.feature.main.match_list

import com.begin_a_gain.core.base.BaseState
import com.begin_a_gain.domain.model.match.MatchItem
import com.begin_a_gain.model.type.match.MatchStatus
import org.joda.time.DateTime

data class OmokMatchListState(
    override val loadingCount: Int = 0,
    val currentDate: DateTime = DateTime.now(),
    val omokMatches: List<MatchItem> = listOf(
        MatchItem(status = MatchStatus.Todo, name = "1일 1Commit"),
        MatchItem(status = MatchStatus.Done, name = "명상하기"),
        MatchItem(status = MatchStatus.Skip, name = "블로그 쓰기")
    ) + (1..5).map {
        MatchItem(status = MatchStatus.None)
    }
): BaseState {
    override fun updateLoadingCount(count: Int): BaseState = copy(loadingCount = count)
}

interface OmokMatchListSideEffect {

}