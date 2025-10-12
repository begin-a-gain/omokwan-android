package com.begin_a_gain.feature.match.common

import androidx.lifecycle.ViewModel
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchCategoryViewModel @Inject constructor(
    private val localRepository: LocalRepository
): ViewModel() {

    private val _categoryList = MutableStateFlow<List<MatchCategoryItem>>(emptyList())
    val categoryList = _categoryList.asStateFlow()

    init {
        _categoryList.value = localRepository.getCategoryList()
    }
}