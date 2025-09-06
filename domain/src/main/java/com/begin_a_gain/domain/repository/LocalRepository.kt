package com.begin_a_gain.domain.repository

interface LocalRepository {

    fun saveIsSignUpCompleted(value: Boolean)
    fun getIsSignUpCompleted(): Boolean
}