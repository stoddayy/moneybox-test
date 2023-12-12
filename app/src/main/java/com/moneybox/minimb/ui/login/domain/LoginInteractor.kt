package com.moneybox.minimb.ui.login.domain

import com.moneybox.minimb.ui.login.data.LoginRepository

fun interface LoginInteractor {
    suspend fun loginWithEmailAndPassword(email: String, password: String)
}

class RemoteLoginInteractor(
    private val loginRepository: LoginRepository
) : LoginInteractor{
    override suspend fun loginWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }
}



