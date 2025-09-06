package com.begin_a_gain.data.repository_impl

import com.begin_a_gain.data.local.data_store.DataStoreManager
import com.begin_a_gain.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject internal constructor(
    private val dataStoreManager: DataStoreManager
): LocalRepository {

    override fun saveIsSignUpCompleted(value: Boolean) {
        dataStoreManager.isSignUpCompleted.data = value
    }

    override fun getIsSignUpCompleted(): Boolean {
        return dataStoreManager.isSignUpCompleted.data
    }
}