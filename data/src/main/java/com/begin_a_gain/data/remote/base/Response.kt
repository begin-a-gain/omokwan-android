package com.begin_a_gain.data.remote.base

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val data: T? = null
)