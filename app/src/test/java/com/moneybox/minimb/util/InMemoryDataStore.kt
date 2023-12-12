package com.moneybox.minimb.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryDataStore<T : Any>(
    defaultValue: T,
) : DataStore<T> {
    private val _data = MutableStateFlow(defaultValue)
    override val data = _data as Flow<T>

    override suspend fun updateData(transform: suspend (t: T) -> T): T {
        val updated = transform(_data.value)
        _data.value = updated
        return updated
    }
}

fun inMemoryPrefsDataStore(preferences: Preferences = emptyPreferences()) = InMemoryDataStore(preferences)