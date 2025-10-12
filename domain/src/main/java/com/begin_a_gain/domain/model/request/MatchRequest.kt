package com.begin_a_gain.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateMatchRequest(
    val name: String,
    val dayType: List<Int>,
    val maxParticipants: Int,
    val categoryCode: String,
    val password: String?,
    val isPublic: Boolean
)