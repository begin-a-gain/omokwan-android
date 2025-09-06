package com.begin_a_gain.data.local.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

internal fun <T> DataStore<Preferences>.getFlow(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> {
    return this.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[key] ?: defaultValue
    }
}

internal suspend fun <T> DataStore<Preferences>.update(
    key: Preferences.Key<T>,
    value: T
) {
    edit { it[key] = value }
}

internal suspend fun <T> DataStore<Preferences>.remove(
    key: Preferences.Key<T>
) {
    edit { it.remove(key) }
}