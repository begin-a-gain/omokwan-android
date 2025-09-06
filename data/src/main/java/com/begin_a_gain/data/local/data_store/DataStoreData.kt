package com.begin_a_gain.data.local.data_store

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

internal abstract class DataStoreData<T> {
    abstract val flow: Flow<T>
    abstract var data: T
    abstract suspend fun remove()

    abstract class StringData<V>(val key: Preferences.Key<String>): DataStoreData<V>()
    abstract class IntData<V>(val key: Preferences.Key<Int>): DataStoreData<V>()
    abstract class BooleanData<V>(val key: Preferences.Key<Boolean>): DataStoreData<V>()
}