package com.moneybox.minimb.ui.login.presentation

import com.moneybox.minimb.util.CoroutineTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val rule = CoroutineTestRule()

    @Test
    fun `initial state is correct`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has typed in the email input, ui state is updated`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has typed in the password input, ui state is updated`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has typed an invalid email address, when tapping login, emailError is true`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has typed an invalid password, when tapping login, passwordError is true`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has typed an invalid email and password, error message is correct`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has entered a valid email and an invalid password, error message is correct`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has entered an invalid email and valid password, error message is correct`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has entered an invalid email address and tapped login, typing again in the email input resets the error`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the the user has entered a valid email, a valid password and the request will fail, request status is correct`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the the user has entered a valid email, a valid password and the request will fail, error message is correct`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `given the user has entered a valid email, password and the request will succeed, correct event is emitted`() {
        TODO("Not yet implemented")
    }
}