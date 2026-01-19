package com.begin_a_gain.feature.match.match

import androidx.lifecycle.ViewModel
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MatchBoardSharedViewModel @Inject constructor(
    private val matchRepository: MatchRepository
): ViewModel() {

    private var _currentMatchId = MutableStateFlow("")
    val currentMatchId = _currentMatchId.asStateFlow()

    private var _isHost = MutableStateFlow(false)
    val isHost = _isHost.asStateFlow()

    fun setCurrentMatchId(matchId: String) {
        _currentMatchId.value = matchId
    }

    fun setIsHost(isHost: Boolean) {
        _isHost.value = isHost
    }
}