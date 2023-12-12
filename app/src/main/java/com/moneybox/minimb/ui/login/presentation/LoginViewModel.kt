package com.moneybox.minimb.ui.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moneybox.minimb.R
import com.moneybox.minimb.data.networking.LocalAuthTokenRepository
import com.moneybox.minimb.data.networking.MoneyBoxApiService
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.data.networking.isRequesting
import com.moneybox.minimb.extensions.dataStore
import com.moneybox.minimb.ui.common.CtaState
import com.moneybox.minimb.ui.common.ResourceString
import com.moneybox.minimb.ui.login.data.RemoteLoginRepository
import com.moneybox.minimb.ui.login.domain.LoginInteractor
import com.moneybox.minimb.ui.login.domain.RemoteLoginInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val interactor: LoginInteractor
) : ViewModel() {

    private val initialState = LoginUiState(
        requestStatus = RequestStatus.DEFAULT,
        email = "",
        password = ""
    )

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    fun onEmailUpdated(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordUpdated(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun onLoginClicked() {

    }

}

data class LoginUiState(
    private val requestStatus: RequestStatus,
    val email: String,
    val password: String
) {
    val ctaState = if (requestStatus.isRequesting()) CtaState.Loading
    else CtaState.Enabled(ResourceString(R.string.log_in))
}

fun loginViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        LoginViewModel(
            interactor = RemoteLoginInteractor(
                loginRepository = RemoteLoginRepository(
                    api = MoneyBoxApiService.instance
                ),
                authTokenRepository = LocalAuthTokenRepository(
                    dataStore = context.dataStore
                )
            )
        )
    }

}