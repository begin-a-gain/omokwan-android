package com.begin_a_gain.data.remote.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    @SerialName("data")
    val data: T? = null
)