package com.moneybox.minimb.ui.planvalue.presentation

import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.ui.common.RawString
import com.moneybox.minimb.ui.planvalue.domain.UiPlanValue
import com.moneybox.minimb.util.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PlanValueViewModelTest {

    @get:Rule
    val rule = CoroutineTestRule()

    @Test
    fun `given the request will fail, request status is correct`() = runTest {
        val expected = RequestStatus.FAILURE

        val viewModel = createViewModel(
            result = Result.failure(Throwable())
        )

        val actual = viewModel.uiState.value.requestStatus

        assertEquals(expected, actual)
    }

    @Test
    fun `given the request will succeed, request status is correct`() = runTest {
        val expected = RequestStatus.SUCCESS

        val viewModel = createViewModel(
            result = Result.success(UiPlanValue(planValue = RawString("£1,000.45")))
        )

        val actual = viewModel.uiState.value.requestStatus

        assertEquals(expected, actual)
    }

    @Test
    fun `given the request will succeed, ui state contains plan value`() = runTest {
        val expected = RawString("£1,000.45")

        val viewModel = createViewModel(
            result = Result.success(UiPlanValue(planValue = RawString("£1,000.45")))
        )

        val actual = viewModel.uiState.value.planValue

        assertEquals(expected, actual)
    }

    private fun createViewModel(
        result: Result<UiPlanValue>
    ) = PlanValueViewModel(
        interactor = { result }
    )
}