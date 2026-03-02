package com.begin_a_gain.domain.model.user

data class NicknameValidation(
    val isValid: Boolean,
    val isDuplicated: Boolean
)