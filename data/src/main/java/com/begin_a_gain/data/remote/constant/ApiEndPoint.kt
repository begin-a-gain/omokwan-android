package com.begin_a_gain.data.remote.constant

sealed class ApiEndPoint(val endPoint: String) {

    data object Auth : ApiEndPoint("auth")
    fun Auth.kakaoSignIn(): String = this.endPoint + "/login/kakao"
    fun Auth.tokenRefresh(): String = this.endPoint + "/token/refresh"

    data object User : ApiEndPoint("users")
    fun User.info() = this.endPoint + "/info"
    fun User.updateNickName() = this.endPoint + "/nicknames"
    fun User.nickNameValidation() = this.endPoint + "/nicknames/validations"
}