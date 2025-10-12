package com.begin_a_gain.domain.model.match

import kotlinx.serialization.Serializable

@Serializable
data class MatchCategoryItem(
    val code: String,
    val name: String,
    val emoji: String
)