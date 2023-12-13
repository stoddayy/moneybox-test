package com.moneybox.minimb.ui.login

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.ui.login.presentation.LoginContent
import com.moneybox.minimb.ui.login.presentation.LoginUiState
import org.junit.Rule
import org.junit.Test

class LoginComposablesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingSpinnerDisplayedWhenRequestingTest() {
        val uiState = LoginUiState(
            requestStatus = RequestStatus.REQUESTING,
            email = "michael@test.com",
            password = "Password",
            emailError = false,
            passwordError = false
        )

        composeTestRule.setContent {
            LoginContent(
                state = uiState,
                onEmailUpdated = {},
                onPasswordUpdated = {},
                onLoginClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Button Loading Spinner").assertIsDisplayed()
    }

    @Test
    fun emailContainsCorrectInputTest() {
        val uiState = LoginUiState(
            requestStatus = RequestStatus.DEFAULT,
            email = "michael@test.com",
            password = "Password",
            emailError = false,
            passwordError = false
        )

        composeTestRule.setContent {
            LoginContent(
                state = uiState,
                onEmailUpdated = {},
                onPasswordUpdated = {},
                onLoginClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Email Input")
            .assert(hasText("michael@test.com"))
    }

    @Test
    fun passwordContainsCorrectInputTest() {
        val uiState = LoginUiState(
            requestStatus = RequestStatus.DEFAULT,
            email = "michael@test.com",
            password = "Password",
            emailError = false,
            passwordError = false
        )

        composeTestRule.setContent {
            LoginContent(
                state = uiState,
                onEmailUpdated = {},
                onPasswordUpdated = {},
                onLoginClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Password Input")
            .assert(hasText("********"))

    }

    @Test
    fun errorMessageIsDisplayedOnRequestFailed() {
        val uiState = LoginUiState(
            requestStatus = RequestStatus.FAILURE,
            email = "michael@test.com",
            password = "Password",
            emailError = false,
            passwordError = false
        )

        composeTestRule.setContent {
            LoginContent(
                state = uiState,
                onEmailUpdated = {},
                onPasswordUpdated = {},
                onLoginClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Login Error Message").assertIsDisplayed()

    }

}