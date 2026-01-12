package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.local.TokenManager
import com.begin_a_gain.data.local.data_store.DataStoreManager
import com.begin_a_gain.domain.model.match.MatchCategoryItem
import com.begin_a_gain.domain.repository.LocalRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalRepositoryImpl @Inject internal constructor(
    private val dataStoreManager: DataStoreManager,
    private val tokenManager: TokenManager
): LocalRepository {

    override fun saveIsSignUpCompleted(value: Boolean) {
        dataStoreManager.isSignUpCompleted.data = value
    }

    override fun getIsSignUpCompleted(): Boolean {
        return dataStoreManager.isSignUpCompleted.data
    }

    override fun saveNickname(value: String) {
        dataStoreManager.nickname.data = value
    }

    override fun getNickname(): String {
        return dataStoreManager.nickname.data
    }

    override fun saveCategoryList(value: List<MatchCategoryItem>) {
        val jsonString = Json.encodeToString(value)
        dataStoreManager.categoryList.data = jsonString
    }

    override fun getCategoryList(): List<MatchCategoryItem> {
        return Json.decodeFromString<List<MatchCategoryItem>>(dataStoreManager.categoryList.data)
    }
}