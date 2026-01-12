package com.begin_a_gain.data.remote.base

import com.begin_a_gain.domain.exception.ErrorResponse
import com.begin_a_gain.domain.exception.SourceException

inline fun <reified T, reified R> callApi(
    call: () -> Response<T>?,
    handleResponse: (T?) -> R?
): Result<R> {
    return try {
        val response = call() ?: return Result.failure(SourceException.InvalidResponse)
        when (response.code) {
            in 200..299 -> {
                val result = handleResponse(response.data)
                if (result == null) Result.failure(SourceException.InvalidResponse)
                else Result.success(result)
            }
            401 -> Result.failure(SourceException.InvalidToken)
            in 400..499 -> {
                Result.failure(SourceException.InvalidRequest(
                    ErrorResponse(
                        code = response.code,
                        status = response.status,
                        message = response.message
                    )
                ))
            }
            else -> Result.failure(SourceException.ServerError)
        }
    } catch (e: Exception) {
        Result.failure(SourceException.Unknown(e))
    }
}