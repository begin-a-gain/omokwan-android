package com.begin_a_gain.core.util

object EmojiUtil {

    fun decodeEmoji(value: String): String {
        val hex = value.removePrefix("U+").removePrefix("u+").trim()
        val codePoint = hex.toInt(16)
        return String(Character.toChars(codePoint))
    }
}