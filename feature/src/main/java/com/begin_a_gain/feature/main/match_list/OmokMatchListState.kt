package com.begin_a_gain.feature.main.match_list

import com.begin_a_gain.domain.model.OmokMatch
import com.begin_a_gain.domain.enum.MatchStatus
import org.joda.time.DateTime

data class OmokMatchListState(
    val currentDate: DateTime = DateTime.now(),
    val omokMatches: List<OmokMatch> = listOf(
        OmokMatch(status = MatchStatus.Todo, title = "1일 1Commit"),
        OmokMatch(status = MatchStatus.Done, title = "명상하기"),
        OmokMatch(status = MatchStatus.Skip, title = "블로그 쓰기")
    ) + (1..5).map {
        OmokMatch(status = MatchStatus.None)
    }
)

interface OmokMatchListSideEffect {

}