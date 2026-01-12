package com.begin_a_gain.data.di

import com.begin_a_gain.data.repository_impl.AuthRepositoryImpl
import com.begin_a_gain.data.repository_impl.LocalRepositoryImpl
import com.begin_a_gain.data.repository_impl.MatchRepositoryImpl
import com.begin_a_gain.data.repository_impl.UserRepositoryImpl
import com.begin_a_gain.domain.repository.AuthRepository
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.MatchRepository
import com.begin_a_gain.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindMatchRepository(
        matchRepositoryImpl: MatchRepositoryImpl
    ): MatchRepository
}