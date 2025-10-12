package com.begin_a_gain.feature.match.join_match

data class JoinMatchState(
    val matchList: List<MatchInfo> = testMatchList,
    val categoryFilter: List<String> = listOf(),
    val availableMatchFilterSelected: Boolean = false,
    val selectedMatchCode: String = ""
)

sealed interface JoinMatchSideEffect {

}

val testMatchList =listOf(
    MatchInfo(
        id = "",
        title = "테스트 1",
        isPrivate = true,
        maximumParticipants = 5,
        currentParticipants = 5,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = true
    ),
    MatchInfo(
        id = "",
        title = "테스트 2",
        isPrivate = false,
        maximumParticipants = 5,
        currentParticipants = 3,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = true
    ),
    MatchInfo(
        id = "",
        title = "테스트 3",
        isPrivate = true,
        maximumParticipants = 5,
        currentParticipants = 5,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = false
    ),
    MatchInfo(
        id = "",
        title = "테스트 4",
        isPrivate = false,
        maximumParticipants = 5,
        currentParticipants = 3,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = false
    ),
    MatchInfo(
        id = "",
        title = "테스트 5",
        isPrivate = true,
        maximumParticipants = 5,
        currentParticipants = 3,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = false
    ),
    MatchInfo(
        id = "",
        title = "테스트 6",
        isPrivate = true,
        maximumParticipants = 5,
        currentParticipants = 5,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = false
    ),
    MatchInfo(
        id = "",
        title = "테스트 7",
        isPrivate = false,
        maximumParticipants = 5,
        currentParticipants = 3,
        category = "테스트",
        date = 10,
        ownerName = "junyoungleee",
        alreadyJoined = false
    )
)

data class MatchInfo(
    val id: String,
    val title: String,
    val isPrivate: Boolean,
    val maximumParticipants: Int,
    val currentParticipants: Int,
    val category: String,
    val date: Int,
    val ownerName: String,
    val alreadyJoined: Boolean
)