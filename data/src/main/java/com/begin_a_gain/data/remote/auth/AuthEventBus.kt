package com.begin_a_gain.data.remote.auth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventBus {
    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    suspend fun send(event: AuthEvent) {
        _events.emit(event)
    }
}

sealed interface AuthEvent {
    data object Logout : AuthEvent
}