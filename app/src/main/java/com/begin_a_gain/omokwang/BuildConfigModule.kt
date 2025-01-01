package com.begin_a_gain.omokwang

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BuildConfigModule {

    @Provides
    @Singleton
    @Named("KAKAO_API_KEY")
    fun provideKakaoApiKey(): String = BuildConfig.KAKAO_API_KEY

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}