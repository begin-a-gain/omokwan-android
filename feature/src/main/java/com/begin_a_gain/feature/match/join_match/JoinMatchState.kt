package com.begin_a_gain.feature.match.join_match

import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.match.MatchInfo

data class JoinMatchState(
    val matchList: List<MatchInfo> = testMatchList,
    val categoryFilter: List<MatchCategoryItem> = listOf(),
    val availableMatchFilterSelected: Boolean = false,
    val selectedMatchCode: String = ""
)

sealed interface JoinMatchSideEffect {

}

val testMatchList =listOf(
    MatchInfo(
        matchId = 1,
        name = "테스트 1",
        public = true,
        maxParticipants = 5,
        participants = 5,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = true
    ),
    MatchInfo(
        matchId = 1,
        name = "테스트 2",
        public = false,
        maxParticipants = 5,
        participants = 3,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = true
    ),
    MatchInfo(
        matchId = 1,
        name = "테스트 3",
        public = true,
        maxParticipants = 5,
        participants = 5,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = false
    ),
    MatchInfo(
        matchId = 1,
        name = "테스트 4",
        public = false,
        maxParticipants = 5,
        participants = 3,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = false
    ),
    MatchInfo(
        matchId = 1,
        name = "테스트 5",
        public = true,
        maxParticipants = 5,
        participants = 3,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = false
    ),
    MatchInfo(
        matchId = 1,
        name = "테스트 6",
        public = true,
        maxParticipants = 5,
        participants = 5,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = false
    ),
    MatchInfo(
        matchId = 1,
        name = "테스트 7",
        public = false,
        maxParticipants = 5,
        participants = 3,
        category = MatchCategoryItem("", "테스트", ""),
        ongoingDays = 10,
        owner = "junyoungleee",
        joinable = false
    )
)