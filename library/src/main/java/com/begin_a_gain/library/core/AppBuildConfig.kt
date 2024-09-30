package com.begin_a_gain.library.core

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AppBuildConfig @Inject constructor(
    @Named("KAKAO_API_KEY") private val kakaoApiKey: String
){
    fun getKakaoApiKey(): String = kakaoApiKey
}