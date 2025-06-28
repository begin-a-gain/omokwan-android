package com.begin_a_gain.data.remote.base

import com.begin_a_gain.domain.exception.SourceException

inline fun <reified T, reified R> callApi(
    call: () -> Response<T>?,
    handleResponse: (T?) -> R?
): Result<R> {
    return try {
        val response = call() ?: return Result.failure(SourceException.InvalidResponse)
        val result = handleResponse(response.data) ?: return Result.failure(SourceException.InvalidResponse)
        return Result.success(result)
    } catch (e: Exception) {
        Result.failure(SourceException.Unknown)
    }
}