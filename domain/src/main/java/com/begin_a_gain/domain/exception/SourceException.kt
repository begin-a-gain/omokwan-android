package com.begin_a_gain.domain.exception

import kotlinx.serialization.Serializable

sealed class SourceException(): Exception() {
    data object InvalidToken: SourceException()
    data object InvalidResponse: SourceException()
    data class InvalidRequest(val error: ErrorResponse): SourceException()
    data object ServerError: SourceException()
    data class Unknown(val e: Exception): SourceException()
}

@Serializable
data class ErrorResponse(
    val code: Int,
    val status: String,
    val message: String,
)