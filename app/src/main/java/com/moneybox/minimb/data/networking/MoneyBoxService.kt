package com.moneybox.minimb.data.networking

import com.moneybox.minimb.data.models.login.LoginRequest
import com.moneybox.minimb.data.models.login.LoginResponse
import com.moneybox.minimb.data.models.products.AllProductsResponse
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MoneyBoxService {

    @POST("users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Result<LoginResponse>

    @GET("investorproducts")
    suspend fun retrieveInvestorProducts(@Header("Authorization") token: String): Result<AllProductsResponse>

}

object MoneyBoxApiService {
    val instance: MoneyBoxService by lazy {
        NetworkingClient.retrofit.create(MoneyBoxService::class.java)
    }
}

