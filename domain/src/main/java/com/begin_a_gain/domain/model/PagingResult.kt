package com.begin_a_gain.domain.model

data class PageResult<T>(
    val items: List<T>,
    val hasNext: Boolean
)

data class BoardPageResult<T>(
    val items: T,
    val hasPrevious: Boolean,
    val hasNext: Boolean
)