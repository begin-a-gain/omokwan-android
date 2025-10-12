package com.begin_a_gain.domain.repository

import com.begin_a_gain.domain.model.match.MatchCategoryItem

interface LocalRepository {

    fun isLoggedOut(): Boolean

    fun saveIsSignUpCompleted(value: Boolean)
    fun getIsSignUpCompleted(): Boolean

    fun saveNickname(value: String)
    fun getNickname(): String

    fun saveCategoryList(value: List<MatchCategoryItem>)
    fun getCategoryList(): List<MatchCategoryItem>
}