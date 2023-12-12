package com.moneybox.minimb.ui.login.presentation

import com.moneybox.minimb.R
import com.moneybox.minimb.data.models.login.LoginResponse
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.ui.common.ResourceString
import com.moneybox.minimb.ui.login.aLoginResponse
import com.moneybox.minimb.util.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val rule = CoroutineTestRule()

    @Test
    fun `initial state is correct`() = runTest {
        val expected = LoginUiState(
            requestStatus = RequestStatus.DEFAULT,
            email = "",
            password = "",
            emailError = false,
            passwordError = false
        )

        val viewModel = createViewModel()

        val actual = viewModel.uiState.value

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has typed in the email input, ui state is updated`() = runTest {
        val expected = "michael@"

        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@")

        val actual = viewModel.uiState.value.email

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has typed in the password input, ui state is updated`() = runTest {
        val expected = "Password"

        val viewModel = createViewModel()

        viewModel.onPasswordUpdated("Password")

        val actual = viewModel.uiState.value.password

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has typed an invalid email address, when tapping login, emailError is true`() = runTest {
        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.emailError

        assertTrue(actual)
    }

    @Test
    fun `given the user has typed an invalid password, when tapping login, passwordError is true`() = runTest {
        val viewModel = createViewModel()

        viewModel.onPasswordUpdated("Pa")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.passwordError

        assertTrue(actual)
    }

    @Test
    fun `given the user has typed an invalid email and password, error message is correct`() = runTest {
        val expected = ResourceString(R.string.email_and_password_error)

        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@")
        viewModel.onPasswordUpdated("Pa")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.errorMessage

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has entered a valid email and an invalid password, error message is correct`() = runTest {
        val expected = ResourceString(R.string.password_error)

        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@test.com")
        viewModel.onPasswordUpdated("Pa")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.errorMessage

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has entered an invalid email and valid password, error message is correct`() = runTest {
        val expected = ResourceString(R.string.email_error)

        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@")
        viewModel.onPasswordUpdated("Password")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.errorMessage

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has entered an invalid email address and tapped login, typing again in the email input resets the error`() = runTest {
        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@")
        viewModel.onPasswordUpdated("Password")
        viewModel.onLoginClicked()
        viewModel.onEmailUpdated("michael@t")

        val actual = viewModel.uiState.value.emailError

        assertFalse(actual)
    }

    @Test
    fun `given the the user has entered a valid email, a valid password and the request will fail, request status is correct`() = runTest {
        val expected = RequestStatus.FAILURE

        val viewModel = createViewModel(
            loginResult = Result.failure(Throwable())
        )

        viewModel.onEmailUpdated("michael@test.com")
        viewModel.onPasswordUpdated("Password")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.requestStatus

        assertEquals(expected, actual)
    }

    @Test
    fun `given the the user has entered a valid email, a valid password and the request will fail, error message is correct`() = runTest {
        val expected = ResourceString(R.string.problem_logging_in)

        val viewModel = createViewModel(
            loginResult = Result.failure(Throwable())
        )

        viewModel.onEmailUpdated("michael@test.com")
        viewModel.onPasswordUpdated("Password")
        viewModel.onLoginClicked()

        val actual = viewModel.uiState.value.errorMessage

        assertEquals(expected, actual)
    }

    @Test
    fun `given the user has entered a valid email, password and the request will succeed, correct nav event is emitted`() = runTest {
        val expected = LoginNavEvent.NextScreen

        val viewModel = createViewModel()

        viewModel.onEmailUpdated("michael@test.com")
        viewModel.onPasswordUpdated("Password")
        viewModel.onLoginClicked()

        val actual = viewModel.navigation.receive()

        assertEquals(expected, actual)
    }

    private fun createViewModel(
        loginResult: Result<Unit> = Result.success(Unit)
    ) = LoginViewModel(
        interactor = { _, _ -> loginResult }
    )
}