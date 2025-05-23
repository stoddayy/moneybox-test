package com.moneybox.minimb.ui.planvalue.data

import com.moneybox.minimb.data.models.products.AllProductsResponse
import com.moneybox.minimb.data.networking.MoneyBoxService
import com.moneybox.minimb.ui.planvalue.presentation.PlanValueViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface PlanValueRepository {
    suspend fun retrievePlanValue(token: String) : Result<AllProductsResponse>
}

class RemotePlanValueRepository(
    private val api: MoneyBoxService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PlanValueRepository {
    override suspend fun retrievePlanValue(token: String): Result<AllProductsResponse> = withContext(dispatcher) {
        val bearerToken = "Bearer $token"
        api.retrieveInvestorProducts(bearerToken)
    }
}