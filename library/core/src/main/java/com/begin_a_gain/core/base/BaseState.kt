package com.begin_a_gain.core.base

interface BaseState {
    val loadingCount: Int
    val isLoading: Boolean get() = loadingCount > 0
    fun updateLoadingCount(count: Int): BaseState
}