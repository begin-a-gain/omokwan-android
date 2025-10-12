package com.begin_a_gain.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MatchCategoryItemResponse(
    val code: String,
    val category: String,
    val emoji: String
)