package com.begin_a_gain.data.local.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "omokwan")
    private val dataStore: DataStore<Preferences> = context.dataStore

    companion object {
        private val SIGN_UP = booleanPreferencesKey("sign_up")
        private val NICKNAME = stringPreferencesKey("nickname")
        private val CATEGORIES = stringPreferencesKey("categories")
    }

    val isSignUpCompleted = object : DataStoreData.BooleanData<Boolean>(SIGN_UP) {
        override val flow: Flow<Boolean>
            get() = dataStore.getFlow(key, false)

        override var data: Boolean
            get() = runBlocking { flow.first() }
            set(value) = runBlocking { dataStore.update(key, value) }

        override suspend fun remove() = dataStore.remove(key)
    }

    val nickname = object : DataStoreData.StringData<String>(NICKNAME) {
        override val flow: Flow<String>
            get() = dataStore.getFlow(key, "")

        override var data: String
            get() = runBlocking { flow.first() }
            set(value) = runBlocking { dataStore.update(key, value) }

        override suspend fun remove() = dataStore.remove(key)
    }

    val categoryList = object : DataStoreData.StringData<String>(CATEGORIES) {
        override val flow: Flow<String>
            get() = dataStore.getFlow(key, "")

        override var data: String
            get() = runBlocking { flow.first() }
            set(value) = runBlocking { dataStore.update(key, value) }

        override suspend fun remove() = dataStore.remove(key)
    }
}