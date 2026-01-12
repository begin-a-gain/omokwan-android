package com.begin_a_gain.domain.model

data class PageResult<T>(
    val items: List<T>,
    val hasNext: Boolean
)