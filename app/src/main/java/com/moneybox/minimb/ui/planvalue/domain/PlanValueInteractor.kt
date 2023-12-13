package com.moneybox.minimb.ui.planvalue.domain

import com.moneybox.minimb.data.networking.AuthTokenRepository
import com.moneybox.minimb.ui.planvalue.data.PlanValueRepository

fun interface PlanValueInteractor {
    suspend fun retrieveUiPlanValue() : Result<UiPlanValue>
}

class RemotePlanValueInteractor(
    private val planValueRepository: PlanValueRepository,
    private val authTokenRepository: AuthTokenRepository
) {

}