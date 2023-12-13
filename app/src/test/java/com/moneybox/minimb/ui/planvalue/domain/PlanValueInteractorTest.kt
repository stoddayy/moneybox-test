package com.moneybox.minimb.ui.planvalue.domain

import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.moneybox.minimb.data.models.products.AllProductsResponse
import com.moneybox.minimb.data.networking.AuthTokenRepository
import com.moneybox.minimb.data.networking.LocalAuthTokenRepository
import com.moneybox.minimb.ui.common.RawString
import com.moneybox.minimb.ui.planvalue.anAllProductsResponse
import com.moneybox.minimb.util.CoroutineTestRule
import com.moneybox.minimb.util.inMemoryPrefsDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class PlanValueInteractorTest {

    @get:Rule
    val rule = CoroutineTestRule()

    @Test
    fun `given the auth token repo contains no token, failure result is emitted`() = runTest {
        val interactor = createInteractor(
            planValueResponse = Result.success(anAllProductsResponse),
            authTokenRepository = LocalAuthTokenRepository(inMemoryPrefsDataStore())
        )

        val actual = interactor.retrieveUiPlanValue()

        assertTrue(actual.isFailure)
    }

    @Test
    fun `given the request will fail, failure result is emitted`() = runTest {
        val interactor = createInteractor(
            planValueResponse = Result.failure(Throwable()),
        )

        val actual = interactor.retrieveUiPlanValue()

        assertTrue(actual.isFailure)
    }

    @Test
    fun `given the request will succeed, a successful result is emitted with a UiPlanValue`() = runTest {
        val expected = Result.success(
            UiPlanValue(planValue = RawString("£1,000.45"))
        )

        val interactor = createInteractor(
            planValueResponse = Result.success(anAllProductsResponse),
        )

        val actual = interactor.retrieveUiPlanValue()

        assertEquals(expected, actual)
    }

    private fun createInteractor(
        planValueResponse: Result<AllProductsResponse>,
        authTokenRepository: AuthTokenRepository = LocalAuthTokenRepository(
            inMemoryPrefsDataStore(
                preferences = preferencesOf(stringPreferencesKey("authTokenKey") to "anAuthToken")
            )
        )
    ) = RemotePlanValueInteractor(
        planValueRepository = { planValueResponse },
        authTokenRepository = authTokenRepository
    )
}