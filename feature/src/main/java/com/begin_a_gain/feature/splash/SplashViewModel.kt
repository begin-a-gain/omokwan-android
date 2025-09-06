package com.begin_a_gain.feature.splash

import androidx.lifecycle.ViewModel
import com.begin_a_gain.domain.repository.LocalRepository
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _isSignIn = MutableStateFlow(false)
    val isSignIn = _isSignIn.asStateFlow()

    init {
        _isSignIn.value = isSignUpCompleted()
    }

    private fun isSignUpCompleted(): Boolean {
        return localRepository.getIsSignUpCompleted()
    }

}