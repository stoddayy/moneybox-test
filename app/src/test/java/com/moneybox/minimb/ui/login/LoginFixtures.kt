package com.moneybox.minimb.ui.login

import com.moneybox.minimb.data.models.login.LoginResponse
import com.moneybox.minimb.data.models.login.SessionDataResponse
import com.moneybox.minimb.data.models.login.UserResponse

val aLoginResponse = LoginResponse(
    session = SessionDataResponse(bearerToken = "a bearer token"),
    user = UserResponse(
        userId = "userId",
        firstName = "Michael",
        lastName = "Stoddart",
        email = "my@email.com"
    )
)