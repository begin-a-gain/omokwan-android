package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.remote.api.UserApi
import com.begin_a_gain.data.remote.base.callApi
import com.begin_a_gain.domain.model.request.NicknameRequest
import com.begin_a_gain.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
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
}