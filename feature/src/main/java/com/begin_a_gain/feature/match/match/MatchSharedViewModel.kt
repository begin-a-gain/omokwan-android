package com.begin_a_gain.feature.match.match

import androidx.lifecycle.ViewModel
import com.begin_a_gain.domain.model.MemberInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchSharedViewModel @Inject constructor(

): ViewModel() {

    private var _currentMatchId = MutableStateFlow(-1)
    val currentMatchId = _currentMatchId.asStateFlow()

    private var _currentParticipants: MutableStateFlow<List<MemberInfo>> = MutableStateFlow(listOf())
    val currentParticipants = _currentParticipants.asStateFlow()

    private var _isHost = MutableStateFlow(false)
    val isHost = _isHost.asStateFlow()

    fun setCurrentMatch(amIHost: Boolean, participants: List<MemberInfo>) {
        _isHost.value = amIHost
        _currentParticipants.value = participants
    }
}