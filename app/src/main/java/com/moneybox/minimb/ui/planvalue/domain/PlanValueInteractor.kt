package com.moneybox.minimb.ui.planvalue.domain

import com.moneybox.minimb.data.models.products.AllProductsResponse
import com.moneybox.minimb.data.networking.AuthTokenRepository
import com.moneybox.minimb.extensions.formatAsCurrency
import com.moneybox.minimb.ui.common.RawString
import com.moneybox.minimb.ui.planvalue.data.PlanValueRepository
import kotlinx.coroutines.flow.first

fun interface PlanValueInteractor {
    suspend fun retrieveUiPlanValue(): Result<UiPlanValue>
}

class RemotePlanValueInteractor(
    private val planValueRepository: PlanValueRepository,
    private val authTokenRepository: AuthTokenRepository
) : PlanValueInteractor {
    override suspend fun retrieveUiPlanValue(): Result<UiPlanValue> {
        val token = authTokenRepository.authToken.first() ?: return Result.failure(Throwable("No token found"))
        return planValueRepository.retrievePlanValue(token)
            .map { it.toUiPlanValue() }
    }
}

private fun AllProductsResponse.toUiPlanValue() = UiPlanValue(
    planValue = RawString(totalPlanValue.formatAsCurrency())
)