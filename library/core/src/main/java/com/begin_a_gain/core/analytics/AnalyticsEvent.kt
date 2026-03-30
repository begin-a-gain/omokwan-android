package com.begin_a_gain.core.analytics


sealed class AnalyticsEvent(
    val name: String,
    val params: Map<String, String> = emptyMap()
) {
    class ButtonClick(buttonName: String, screen: String) : AnalyticsEvent(
        name = "button_click",
        params = mapOf(
            "button_name" to buttonName,
            "screen" to screen
        )
    )

    class Login(method: String) : AnalyticsEvent(
        name = "login",
        params = mapOf("method" to method)
    )

    class SignUp(method: String) : AnalyticsEvent(
        name = "sign_up",
        params = mapOf("method" to method)
    )

    class CreateMatch : AnalyticsEvent(name = "create_match")

    class JoinMatch(matchId: Int) : AnalyticsEvent(
        name = "join_match",
        params = mapOf("match_id" to matchId.toString())
    )

    class CompleteOmok(screen: String) : AnalyticsEvent(
        name = "complete_omok",
        params = mapOf("screen" to screen)
    )

    class Logout : AnalyticsEvent(name = "logout")

    class DeleteAccount : AnalyticsEvent(name = "delete_account")
}