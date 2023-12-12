package com.moneybox.minimb.data.networking

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthTokenRepository {
    suspend fun saveAuthToken(token: String)
    suspend fun authToken(): Flow<String?>
}

class LocalAuthTokenRepository(
    private val dataStore: DataStore<Preferences>
) : AuthTokenRepository {
    private val authTokenKey = stringPreferencesKey("authTokenKey")

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { prefs ->
            prefs[authTokenKey] = token
        }
    }

    override suspend fun authToken() = dataStore.data
        .map { it[authTokenKey] }
}