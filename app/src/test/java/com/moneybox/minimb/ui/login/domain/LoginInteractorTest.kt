package com.moneybox.minimb.ui.login.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.moneybox.minimb.data.models.login.LoginResponse
import com.moneybox.minimb.data.networking.AuthTokenRepository
import com.moneybox.minimb.data.networking.LocalAuthTokenRepository
import com.moneybox.minimb.ui.login.aLoginResponse
import com.moneybox.minimb.util.CoroutineTestRule
import com.moneybox.minimb.util.InMemoryDataStore
import com.moneybox.minimb.util.inMemoryPrefsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class LoginInteractorTest {

    @get:Rule
    val rule = CoroutineTestRule()

    @Test
    fun `given the request will fail, auth token repo is not updated`() = runTest {
        val repo = LocalAuthTokenRepository(inMemoryPrefsDataStore())

        val interactor = createInteractor(
            loginResult = Result.failure(Throwable()),
            authTokenRepository = repo
        )

        interactor.loginWithEmailAndPassword("email", "password")

        val actual = repo.authToken.first()

        assertNull(actual)
    }

    @Test
    fun `given the request will succeed, auth token repo contains the token`() = runTest {
        val repo = LocalAuthTokenRepository(inMemoryPrefsDataStore())

        val interactor = createInteractor(
            loginResult = Result.success(aLoginResponse),
            authTokenRepository = repo
        )

        interactor.loginWithEmailAndPassword("email", "password")

        val actual = repo.authToken.first()

        assertEquals(aLoginResponse.session.bearerToken, actual)
    }

    private fun createInteractor(
        loginResult: Result<LoginResponse>,
        authTokenRepository: AuthTokenRepository
    ) = RemoteLoginInteractor(
        loginRepository = { _, _ -> loginResult },
        authTokenRepository = authTokenRepository
    )
}

