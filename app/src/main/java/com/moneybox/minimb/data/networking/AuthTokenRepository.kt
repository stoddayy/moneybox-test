package com.moneybox.minimb.data.networking

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

interface AuthTokenRepository {
    suspend fun saveAuthToken(token: String)
    suspend fun authToken() : String?
}

class LocalAuthTokenRepository(
    private val dataStore: DataStore<Preferences>
) : AuthTokenRepository{
    override suspend fun saveAuthToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun authToken(): String? {
        TODO("Not yet implemented")
    }

}