package com.moneybox.minimb.data.networking

interface AuthTokenDataStore {
    suspend fun saveAuthToken(token: String)
    suspend fun authToken() : String?
}