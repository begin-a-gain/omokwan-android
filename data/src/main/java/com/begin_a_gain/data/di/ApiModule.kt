package com.begin_a_gain.data.di

import android.util.Log
import com.begin_a_gain.data.local.TokenManager
import com.begin_a_gain.data.remote.base.Response
import com.begin_a_gain.data.remote.constant.ApiEndPoint
import com.begin_a_gain.data.remote.constant.ApiEndPoint.Auth.tokenRefresh
import com.begin_a_gain.data.remote.constant.NETWORK_TIME_OUT
import com.begin_a_gain.data.remote.response.TokenResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.cookie
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.parseServerSetCookieHeader
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        @Named("BASE_URL") baseUrl: String,
        tokenManager: TokenManager
    ): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = NETWORK_TIME_OUT
                connectTimeoutMillis = NETWORK_TIME_OUT
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("Network", message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.i("Network", "HTTP status: ${response.status.value}")
                }
            }

            defaultRequest {
                url(baseUrl)
                accept(ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                if (tokenManager.getAccessToken().isNotBlank()) {
                    header("Authorization", tokenManager.getAccessToken())
                }
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        null
                    }

                    refreshTokens {
                        var refreshToken = tokenManager.getRefreshToken()
                        val httpResponse = this.client.post(ApiEndPoint.Auth.tokenRefresh()) {
                            markAsRefreshTokenRequest()
                            cookie("refresh_token", tokenManager.getRefreshToken())
                        }

                        httpResponse.headers.getAll("Set-Cookie")
                            ?.map { parseServerSetCookieHeader(it) }
                            ?.find { it.name == "refresh_token" }
                            ?.value?.let {
                                tokenManager.saveRefreshToken(it)
                                refreshToken = it
                            }

                        val tokens = httpResponse.body<Response<TokenResponse>>().data
                        tokens?.let {
                            tokenManager.saveTokens(
                                accessToken = tokens.accessToken?: "",
                                refreshToken = refreshToken
                            )
                            BearerTokens(
                                accessToken = tokens.accessToken?: "",
                                refreshToken = refreshToken
                            )
                        }
                    }
                }
            }
        }
    }
}
