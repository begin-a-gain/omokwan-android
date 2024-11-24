package com.begin_a_gain.feature.main.omoklist

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class OmokListViewModel(

): ViewModel(), ContainerHost<OmokListState, OmokListSideEffect> {

    override val container: Container<OmokListState, OmokListSideEffect> = container(OmokListState())
}