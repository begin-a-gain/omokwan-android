package com.begin_a_gain.library.core

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AppBuildConfig @Inject constructor(
    @Named("KAKAO_API_KEY") private val kakaoApiKey: String,
    @Named("BASE_URL") private val baseURL: String
){
    fun getKakaoApiKey(): String = kakaoApiKey
    fun getBaseUrl(): String = baseURL
}