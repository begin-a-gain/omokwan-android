package com.begin_a_gain.feature.main.my_page

import com.begin_a_gain.core.base.BaseViewModel
import com.begin_a_gain.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageListViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel<MyPageState, Nothing>(MyPageState()) {

    fun initiate() {
        withLoading {
            userRepository.getUserMyPage()
                .onSuccess {
                    intent {
                        reduce {
                            state.copy(
                                nickname = it.nickname,
                                inProgressMatches = it.inProgressMatchList,
                                completedMatches = it.completedMatchList
                            )
                        }
                    }
                }
        }
    }
}
