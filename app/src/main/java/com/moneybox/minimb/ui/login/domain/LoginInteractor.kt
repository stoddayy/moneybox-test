package com.moneybox.minimb.ui.login.domain

import com.moneybox.minimb.data.networking.AuthTokenRepository
import com.moneybox.minimb.extensions.doOnSuccess
import com.moneybox.minimb.ui.login.data.LoginRepository

fun interface LoginInteractor {
    suspend fun loginWithEmailAndPassword(email: String, password: String) : Result<Unit>
}

class RemoteLoginInteractor(
    private val loginRepository: LoginRepository,
    private val authTokenRepository: AuthTokenRepository
) : LoginInteractor{
    override suspend fun loginWithEmailAndPassword(email: String, password: String) =
        loginRepository.login(email, password)
            .doOnSuccess {
                authTokenRepository.saveAuthToken(it.session.bearerToken)
            }.map { }
    }



