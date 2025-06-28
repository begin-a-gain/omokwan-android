package com.begin_a_gain.domain.exception

sealed class SourceException(): Exception() {
    object InvalidToken: SourceException()
    object InvalidResponse: SourceException()
    object Unknown: SourceException()
}