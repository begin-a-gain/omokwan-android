package com.begin_a_gain.omokwang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.begin_a_gain.data.remote.auth.AuthEvent
import com.begin_a_gain.data.remote.auth.AuthEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(MainState())

    init {
        observeAuthEvents()
    }

    private fun observeAuthEvents() {
        viewModelScope.launch {
            AuthEventBus.events.collect { event ->
                when (event) {
                    AuthEvent.Logout -> {
                        intent {
                            postSideEffect(MainSideEffect.Logout)
                        }
                    }
                }
            }
        }
    }
}