package com.begin_a_gain.domain.exception

sealed class SourceException(): Exception() {
    data object InvalidToken: SourceException()
    data object InvalidResponse: SourceException()
    data object InvalidRequest: SourceException()
    data object NetworkError: SourceException()
    data class Unknown(val e: Exception): SourceException()
}