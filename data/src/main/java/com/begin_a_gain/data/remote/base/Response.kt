package com.begin_a_gain.data.remote.base

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val code: Int,
    val status: String,
    val message: String,
    val data: T? = null
)