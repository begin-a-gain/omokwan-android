package com.begin_a_gain.data.remote.constant

sealed class ApiEndPoint(val endPoint: String) {

    data object Auth : ApiEndPoint("auth")
    fun Auth.kakaoSignIn(): String = this.endPoint + "/login/kakao"
    fun Auth.tokenRefresh(): String = this.endPoint + "/token/refresh"

    data object User : ApiEndPoint("users")
    fun User.info() = this.endPoint + "/info"
    fun User.nickname() = this.endPoint + "/nicknames"
    fun User.nicknameValidation() = this.endPoint + "/nicknames/validations"
    fun User.myPage(userId: Int) = this.endPoint + "/$userId/mypage"
    fun User.me() = this.endPoint + "/me"
    fun User.deletionSurvey() = this.endPoint + "me/deletion-survey"

    data object Match : ApiEndPoint("matches")
    fun Match.categories() = this.endPoint + "/categories"
    fun Match.create() = this.endPoint
    fun Match.get() = this.endPoint
    fun Match.all() = this.endPoint + "/all"
    fun Match.board(matchId: Int) = this.endPoint + "/$matchId/board"
    fun Match.participants(matchId: Int) = this.endPoint + "/$matchId/participants"
    fun Match.deleteMe(matchId: Int) = this.endPoint + "/$matchId/participants/me"
    fun Match.settings(matchId: Int) = this.endPoint + "/$matchId/settings"
    fun Match.changeHost(matchId: Int) = this.endPoint + "/$matchId/host"
    fun Match.kickUser(matchId: Int, userId: Int) = this.endPoint + "/$matchId/users/$userId/kick"
    fun Match.status(matchId: Int) = this.endPoint + "/$matchId/status"
}