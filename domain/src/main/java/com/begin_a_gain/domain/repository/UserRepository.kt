package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.request.DeletionSurveyRequest
import com.begin_a_gain.domain.model.user.MyPageInfo
import com.begin_a_gain.domain.model.user.NicknameValidation
import com.begin_a_gain.domain.model.user.UserInfo
import com.begin_a_gain.domain.model.user.UserPaging

interface UserRepository {
    suspend fun postNicknameValidation(nickname: String): Result<NicknameValidation>
    suspend fun postNickname(nickname: String): Result<Unit>
    suspend fun getUserInfo (): Result<UserInfo>
    suspend fun getUserMyPage(): Result<MyPageInfo>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun postDeletionSurvey(request: DeletionSurveyRequest): Result<Unit>
    suspend fun getUsersPaging(nickname: String, cursor: String, pageSize: Int): Result<UserPaging>
}