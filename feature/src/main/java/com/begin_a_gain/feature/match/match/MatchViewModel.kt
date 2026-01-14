package com.begin_a_gain.feature.match.match

import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
): BaseViewModel<MatchState, Nothing>(MatchState()) {

}