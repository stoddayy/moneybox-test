package com.moneybox.minimb.data.networking

import com.moneybox.minimb.data.models.login.LoginRequest
import com.moneybox.minimb.data.models.login.LoginResponse
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface MoneyBoxService {

    @POST("users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest) : Result<LoginResponse>

}

object MoneyBoxApiService {
    val instance : MoneyBoxService by lazy {
        NetworkingClient.retrofit.create(MoneyBoxService::class.java)
    }
}

