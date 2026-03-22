package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.local.TokenManager
import com.begin_a_gain.data.remote.api.UserApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.model.request.DeletionSurveyRequest
import com.begin_a_gain.domain.model.request.NicknameRequest
import com.begin_a_gain.domain.model.user.MyPageInfo
import com.begin_a_gain.domain.model.user.MyPageMatchItem
import com.begin_a_gain.domain.model.user.NicknameValidation
import com.begin_a_gain.domain.model.user.User
import com.begin_a_gain.domain.model.user.UserInfo
import com.begin_a_gain.domain.model.user.UserPaging
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
    private val localRepository: LocalRepository
) : UserRepository {

    override suspend fun postNickname(nickname: String): Result<Unit> {
        return callApi(
            call = {
                userApi.putNickname(NicknameRequest(nickname))
            },
            handleResponse = {}
        )
    }

    override suspend fun postNicknameValidation(nickname: String): Result<NicknameValidation> {
        return callApi(
            call = {
                userApi.postNicknameValidation(NicknameRequest(nickname))
            },
            handleResponse = {
                NicknameValidation(
                    isValid = it?.isValid?: false,
                    isDuplicated = it?.isDuplicated?: false
                )
            }
        )
    }

    override suspend fun getUserInfo(): Result<UserInfo> {
        return callApi(
            call = {
                userApi.getUserInfo()
            },
            handleResponse = { response ->
                response?.let {
                    response.refreshToken?.let { refreshToken ->
                        tokenManager.saveRefreshToken(refreshToken)
                    }
                    response.nickname?.let { nickname ->
                        localRepository.saveNickname(nickname)
                    }
                    response.id?.let { userId ->
                        localRepository.saveUserId(userId)
                    }

                    UserInfo(
                        id = it.id ?: 0,
                        socialId = response.socialId ?: 0,
                        nickname = response.nickname ?: "",
                        refreshToken = response.refreshToken ?: "",
                        deleted = response.deleted ?: false
                    )
                }
            }
        )
    }

    override suspend fun getUserMyPage(): Result<MyPageInfo> {
        val userId = localRepository.getUserId()
        return callApi(
            call = {
                userApi.getMyPage(userId = userId)
            },
            handleResponse = { response ->
                response?.let {
                    MyPageInfo(
                        nickname = it.nickname,
                        inProgressMatchList = it.inProgressMatches.map { item ->
                            MyPageMatchItem(
                                matchId = item.matchId,
                                title = item.matchName,
                                ongoingDays = item.participantDays,
                                combo = item.comboCount,
                                omok = item.omokCount,
                                repeatDays = item.dayOfWeeks
                            )
                        },
                        completedMatchList = it.completedMatches.map { item ->
                            MyPageMatchItem(
                                matchId = item.matchId,
                                title = item.matchName,
                                ongoingDays = item.participantDays,
                                combo = item.comboCount,
                                omok = item.omokCount,
                                repeatDays = item.dayOfWeeks
                            )
                        }
                    )
                }
            }
        )
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return callApi(
            call = {
                userApi.deleteAccount()
            },
            handleResponse = {
                null
            }
        )
    }

    override suspend fun postDeletionSurvey(request: DeletionSurveyRequest): Result<Unit> {
        return callApi(
            call = {
                userApi.postDeletionSurvey(request)
            },
            handleResponse = {
                null
            }
        )
    }

    override suspend fun getUsersPaging(
        nickname: String,
        cursor: String,
        pageSize: Int
    ): Result<UserPaging> {
        return callApi(
            call = {
                userApi.getUsers(nickname, cursor, pageSize)
            },
            handleResponse = { response ->
                UserPaging(
                    users = response?.users?.map {
                        User(
                            userId = it.userId,
                            nickname = it.nickname
                        )
                    } ?: emptyList(),
                    nextCursor = response?.nextCursor,
                    hasNext = response?.hasNext ?: false
                )
            }
        )
    }
}