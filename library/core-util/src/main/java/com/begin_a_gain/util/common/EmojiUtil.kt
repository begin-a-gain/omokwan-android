package com.begin_a_gain.util.common

object EmojiUtil {

    fun decodeEmoji(value: String): String {
        val hex = value.removePrefix("U+").removePrefix("u+").trim()
        val codePoint = hex.toInt(16)
        return String(Character.toChars(codePoint))
    }
}