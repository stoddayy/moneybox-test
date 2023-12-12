package com.moneybox.minimb.ui.login.data

import com.moneybox.minimb.data.models.login.LoginRequest
import com.moneybox.minimb.data.models.login.LoginResponse
import com.moneybox.minimb.data.networking.MoneyBoxService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
}

class RemoteLoginRepository(
    private val api: MoneyBoxService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginRepository {
    override suspend fun login(email: String, password: String) = withContext(dispatcher) {
        val loginRequest = LoginRequest(
            email = email,
            password = password
        )

        api.loginUser(loginRequest = loginRequest)
    }
}