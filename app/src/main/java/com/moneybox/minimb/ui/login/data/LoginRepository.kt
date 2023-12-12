package com.moneybox.minimb.ui.login.data

interface LoginRepository {
    suspend fun login(email:String, password: String)
}

class RemoteLoginRepository(

) : LoginRepository {
    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

}