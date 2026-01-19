package com.begin_a_gain.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseViewModel<S : BaseState, SE : Any>(
    initialState: S
) : ContainerHost<S, SE>, ViewModel() {

    override val container: Container<S, SE> = container(
        initialState = initialState
    )

    protected fun CoroutineScope.withLoading(block: suspend () -> Unit) {
        launch {
            intent {
                reduce { state.updateLoadingCount(state.loadingCount + 1) as S }
            }
            try {
                block()
            } finally {
                intent {
                    reduce { state.updateLoadingCount((state.loadingCount - 1).coerceAtLeast(0)) as S }
                }
            }
        }
    }
}