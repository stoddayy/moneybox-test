package com.moneybox.minimb.ui.planvalue

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.ui.common.EmptyString
import com.moneybox.minimb.ui.common.RawString
import com.moneybox.minimb.ui.planvalue.presentation.PlanValueContent
import com.moneybox.minimb.ui.planvalue.presentation.PlanValueUiState
import org.junit.Rule
import org.junit.Test

class PlanValueComposablesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingSpinnerDisplayedWhenRequestingTest() {
        val uiState = PlanValueUiState(
            requestStatus = RequestStatus.REQUESTING,
            planValue = EmptyString
        )

        composeTestRule.setContent {
            PlanValueContent(
                state = uiState,
                onRetry = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Loading Spinner").assertIsDisplayed()
    }

    @Test
    fun retryButtonDisplayedWhenRequestFailureTest() {
        val uiState = PlanValueUiState(
            requestStatus = RequestStatus.FAILURE,
            planValue = EmptyString
        )

        composeTestRule.setContent {
            PlanValueContent(
                state = uiState,
                onRetry = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Retry Button").assertIsDisplayed()
    }

    @Test
    fun planValueTextDisplayedWhenRequestSuccessTest() {
        val uiState = PlanValueUiState(
            requestStatus = RequestStatus.SUCCESS,
            planValue = RawString("£1,000.45")
        )

        composeTestRule.setContent {
            PlanValueContent(
                state = uiState,
                onRetry = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Plan Value Text").assertIsDisplayed()
    }

    @Test
    fun planValueContainsCorrectValueOnSuccessTest() {
        val uiState = PlanValueUiState(
            requestStatus = RequestStatus.SUCCESS,
            planValue = RawString("£1,000.45")
        )

        composeTestRule.setContent {
            PlanValueContent(
                state = uiState,
                onRetry = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Plan Value Text")
            .assert(hasText("£1,000.45"))
    }
}