package com.begin_a_gain.data.repository_impl

import android.util.Log
import com.begin_a_gain.data.local.TokenManager
import com.begin_a_gain.data.remote.api.UserApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.model.request.NicknameRequest
import com.begin_a_gain.domain.model.user.UserInfo
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

    override suspend fun postNicknameValidation(nickname: String): Result<Unit> {
        return callApi(
            call = {
                userApi.postNicknameValidation(NicknameRequest(nickname))
            },
            handleResponse = {}
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
}