package com.begin_a_gain.feature.match.match

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchSharedViewModel @Inject constructor(

): ViewModel() {

    private var _currentMatchId = MutableStateFlow(-1)
    val currentMatchId = _currentMatchId.asStateFlow()

    private var _currentHostId = MutableStateFlow(-1)
    val currentHostId = _currentHostId.asStateFlow()

    private var _isHost = MutableStateFlow(false)
    val isHost = _isHost.asStateFlow()

    fun setCurrentMatchId(id: Int) {
        _currentMatchId.value = id
    }

    fun setCurrentHost(id: Int, amIHost: Boolean) {
        _currentHostId.value = id
        _isHost.value = amIHost
    }
}