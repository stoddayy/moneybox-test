package com.moneybox.minimb.data.networking

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthTokenRepository {
    val authToken: Flow<String?>
    suspend fun saveAuthToken(token: String)
}

class LocalAuthTokenRepository(
    private val dataStore: DataStore<Preferences>
) : AuthTokenRepository {
    private val authTokenKey = stringPreferencesKey("authTokenKey")

    override val authToken: Flow<String?> = dataStore.data
        .map { it[authTokenKey] }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { prefs ->
            prefs[authTokenKey] = token
        }
    }
}