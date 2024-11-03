package com.begin_a_gain.feature.sign_up

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : ViewModel(), ContainerHost<SignUpState, SignUpSideEffect> {

    override val container: Container<SignUpState, SignUpSideEffect> = container(SignUpState())

    fun setNickname(nickname: String) = blockingIntent {
        reduce { state.copy(nickname = nickname) }
    }

}