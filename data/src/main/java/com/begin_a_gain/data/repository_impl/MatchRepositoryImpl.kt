package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.remote.api.MatchApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.enum.MatchCalendarStatus
import com.begin_a_gain.domain.model.BoardPageResult
import com.begin_a_gain.domain.model.MemberInfo
import com.begin_a_gain.domain.model.PageResult
import com.begin_a_gain.domain.model.match.MatchBoard
import com.begin_a_gain.domain.model.match.MatchBoardDate
import com.begin_a_gain.domain.model.match.MatchBoardInitialInfo
import com.begin_a_gain.domain.model.match.MatchBoardUser
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.model.match.MatchDatesUserStatus
import com.begin_a_gain.domain.model.match.MatchInfo
import com.begin_a_gain.domain.model.match.MatchSettings
import com.begin_a_gain.domain.model.match.MyMatchItem
import com.begin_a_gain.domain.model.request.ChangeMatchHostRequest
import com.begin_a_gain.domain.model.request.CreateMatchRequest
import com.begin_a_gain.domain.model.request.JoinMatchRequest
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.model.type.match.MatchJoinStatus.Companion.toMatchJoinStatus
import com.begin_a_gain.model.type.match.MatchStatus
import com.begin_a_gain.util.common.DateTimeUtil.toString
import com.begin_a_gain.util.common.ODateTimeFormat
import org.joda.time.DateTime
import javax.inject.Inject

class MatchRepositoryImpl @Inject internal constructor(
    private val matchApi: MatchApi,
    private val localRepository: LocalRepository
): MatchRepository {

    override suspend fun getMatchCategoryList(): Result<Boolean> {
        return callApi(
            call = {
                matchApi.getMatchCategoryList()
            },
            handleResponse = { response ->
                response?.let {
                    if (it.isEmpty()) false
                    else {
                        val categoryList = it.map { category ->
                            MatchCategoryItem(
                                code = category.code,
                                name = category.category,
                                emoji = category.emoji
                            )
                        }
                        localRepository.saveCategoryList(categoryList)
                        true
                    }
                }
            }
        )
    }

    override suspend fun postCreateMatch(request: CreateMatchRequest): Result<Int> {
        return callApi(
            call = {
                matchApi.postMatch(request)
            },
            handleResponse = {
                it?.matchId ?: -1
            }
        )
    }

    override suspend fun getMyDailyMatchList(date: String): Result<List<MyMatchItem>> {
        return callApi(
            call = {
                matchApi.getMyMatch(date)
            },
            handleResponse = { response ->
                response?.map {
                    MyMatchItem(
                        matchId = it.matchId,
                        name = it.name,
                        ongoingDays = it.ongoingDays,
                        participants = it.participants,
                        maxParticipants = it.maxParticipants,
                        public = it.public,
                        status = MatchStatus.getMatchStatus(
                            date = date,
                            isCompleted = it.completed
                        )
                    )
                }?: emptyList()
            }
        )
    }

    override suspend fun getAllMatchPagingItems(
        pageNumber:Int,
        pageSize: Int,
        category: List<Int>,
        joinable: Boolean,
        keyword: String
    ): PageResult<MatchInfo> {
        val response = matchApi.getAllMatchesPaging(pageNumber, pageSize, category, joinable, keyword)
        val matchList = response.data?.matchList?.map {
            val category = localRepository.getCategoryList().firstOrNull { category ->
                category.code.toInt() == it.categoryId
            }
            MatchInfo(
                matchId = it.matchId,
                name = it.name,
                ongoingDays = it.ongoingDays,
                participants = it.participants,
                maxParticipants = it.maxParticipants,
                category = category,
                public = it.public,
                owner = it.hostName,
                status = it.joinable.toMatchJoinStatus()
            )
        }?: emptyList()

        return PageResult(
            items = matchList,
            hasNext = response.data?.hasNext ?: false
        )
    }

    override suspend fun postJoinMatch(matchId: Int, request: JoinMatchRequest): Result<Boolean> {
        return callApi(
            call = {
                matchApi.postMatchParticipants(matchId, request)
            },
            handleResponse = {
                it?.matchId != null
            }
        )
    }

    override suspend fun getMatchBoardInitialData(matchId: Int, date: String): Result<MatchBoardInitialInfo> {
        return callApi(
            call = {
                matchApi.getMatchBoard(matchId, date, 1)
            },
            handleResponse = {
                MatchBoardInitialInfo(
                    users = it?.users?.map { user ->
                        MatchBoardUser(
                            userId = user.userId,
                            nickname = user.nickname,
                            isHost = user.isHost
                        )
                    } ?: emptyList(),
                    isTodayMatchCompleted = it?.isTodayMatchCompleted ?: false
                )
            }
        )
    }

    override suspend fun getMatchBoardPaging(matchId: Int, date: String, pageSize: Int): BoardPageResult<MatchBoard> {
        val response = matchApi.getMatchBoard(matchId, date, pageSize)
        val boardResult = response.data.let {
            MatchBoard(
                users = it?.users?.map { user ->
                    MatchBoardUser(
                        userId = user.userId,
                        nickname = user.nickname,
                        isHost = user.isHost
                    )
                }?: emptyList(),
                dates = it?.dates?.map { date ->
                    MatchBoardDate(
                        date = date.date,
                        userStatus = date.userStatus.mapIndexed { index, userStatus ->
                            MatchDatesUserStatus(
                                userId = userStatus.userId,
                                status = when {
                                    userStatus.isCombo -> MatchCalendarStatus.Combo
                                    userStatus.isCompleted -> MatchCalendarStatus.Done
                                    else -> {
                                        if (index == 0 && date.date == DateTime.now().toString(ODateTimeFormat.DateForNetwork)) {
                                            MatchCalendarStatus.Todo
                                        } else {
                                            MatchCalendarStatus.None
                                        }
                                    }
                                }
                            )
                        }
                    )
                }?: emptyList(),
                nextCursor = it?.nextCursor ?: DateTime.now().toString(ODateTimeFormat.DateForNetwork),
                prevCursor = it?.prevCursor ?: DateTime.now().toString(ODateTimeFormat.DateForNetwork),
                hasNext = it?.hasNext ?: false,
                hasPrev = it?.hasPrev ?: false,
                isTodayMatchCompleted = it?.isTodayMatchCompleted ?: false
            )
        }

        return BoardPageResult(
            items = boardResult,
            hasNext = boardResult.hasNext,
            hasPrevious = boardResult.hasPrev,
        )
    }

    override suspend fun getParticipants(matchId: Int): Result<List<MemberInfo>> {
        return callApi(
            call = {
                matchApi.getParticipants(matchId)
            },
            handleResponse = {
                it?.userInfo?.map { user ->
                    MemberInfo(
                        id = user.userId,
                        name = user.nickname,
                        combo = user.combo,
                        days = user.ongoingDays,
                        omok = user.omokCount
                    )
                }?: emptyList()
            }
        )
    }

    override suspend fun getMatchSettings(matchId: Int): Result<MatchSettings> {
        return callApi(
            call = {
                matchApi.getMatchSettings(matchId)
            },
            handleResponse = {
                val categoryItem = localRepository.getCategoryList().firstOrNull { category ->
                    category.code == it?.categoryCode
                }

                MatchSettings(
                    name = it?.name ?: "",
                    matchCode = it?.matchCode ?: "",
                    ongoingDays = it?.ongoingDays ?: 0,
                    repeatDayTypes = it?.repeatDayTypes ?: emptyList(),
                    maxParticipants = it?.maxParticipants ?: 0,
                    category = categoryItem,
                    isPublic = it?.isPublic ?: true,
                    password = it?.password ?: ""
                )
            }
        )
    }

    override suspend fun postChangeHost(matchId: Int, newHostId: Int): Result<Boolean> {
        return callApi(
            call = {
                matchApi.putNewHost(matchId, ChangeMatchHostRequest(newHostId))
            },
            handleResponse = {
                it?.hostId == newHostId
            }
        )
    }

    override suspend fun deleteMe(matchId: Int): Result<Boolean> {
        val myId = localRepository.getUserId()
        return callApi(
            call = {
                matchApi.deleteMe(matchId)
            },
            handleResponse = {
                it?.userId == myId
            }
        )
    }

    override suspend fun postKickUser(matchId: Int, userId: Int): Result<Boolean> {
        return callApi(
            call = {
                matchApi.postKickUser(matchId, userId)
            },
            handleResponse = {
                it?.userId == userId
            }
        )
    }

    override suspend fun putMatchStatus(matchId: Int): Result<Boolean> {
        return callApi(
            call = {
                matchApi.putMatchStatus(matchId)
            },
            handleResponse = {
                it?.completed ?: false
            }
        )
    }
}