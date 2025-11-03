package com.begin_a_gain.feature.splash

import androidx.lifecycle.viewModelScope
import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<SplashState, SplashSideEffect>() {

    override val container: Container<SplashState, SplashSideEffect> = container(SplashState())

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo()
                .onSuccess {
                    if (it.nickname.isBlank()) {
                        intent { postSideEffect(SplashSideEffect.NotFinishedSignUp) }
                    } else {
                        intent { postSideEffect(SplashSideEffect.LoggedIn) }
                    }
                }
                .onFailure {
                    delay(2_000L)
                }
        }
    }
}