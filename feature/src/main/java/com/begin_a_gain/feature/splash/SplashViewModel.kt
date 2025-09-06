package com.begin_a_gain.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.UserRepository
import com.kakao.sdk.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _isSignIn = MutableStateFlow(false)
    val isSignIn = _isSignIn.asStateFlow()

    init {
        viewModelScope.launch {
            if (isSignUpCompleted()) {
                getUserInfo()
            }
        }
    }

    private fun isSignUpCompleted(): Boolean {
        val isSignUpCompleted = localRepository.getIsSignUpCompleted()
        _isSignIn.value = isSignUpCompleted
        return isSignUpCompleted
    }

    private suspend fun getUserInfo() {
        userRepository.getUserInfo()
    }
}