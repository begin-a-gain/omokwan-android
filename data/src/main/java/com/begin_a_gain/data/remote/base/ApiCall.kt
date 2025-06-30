package com.begin_a_gain.data.remote.base

import com.begin_a_gain.domain.exception.SourceException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import java.io.IOException

inline fun <reified T, reified R> callApi(
    call: () -> Response<T>?,
    handleResponse: (T?) -> R?
): Result<R> {
    return try {
        val response = call() ?: return Result.failure(SourceException.InvalidResponse)
        val result = handleResponse(response.data) ?: return Result.failure(SourceException.InvalidResponse)
        return Result.success(result)
    } catch (e: ClientRequestException) {
        when(e.response.status) {
            HttpStatusCode.Unauthorized -> Result.failure(SourceException.InvalidToken)
            else -> Result.failure(SourceException.InvalidRequest)
        }
    } catch (e: ServerResponseException) {
        Result.failure(SourceException.NetworkError)
    } catch (e: IOException) {
        Result.failure(SourceException.NetworkError)
    } catch (e: Exception) {
        Result.failure(SourceException.Unknown(e))
    }
}