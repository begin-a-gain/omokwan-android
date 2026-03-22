package com.begin_a_gain.domain.enum

enum class NotificationType {
    MATCH_INVITED, MATCH_JOINED, MEMBER_LEFT, HOST_CHANGED;

    companion object {
        fun String.parse(): NotificationType {
            return when (this) {
                "MATCH_INVITED" -> MATCH_INVITED
                "MATCH_JOINED" -> MATCH_JOINED
                "MEMBER_LEFT" -> MEMBER_LEFT
                else -> HOST_CHANGED
            }
        }
    }
}