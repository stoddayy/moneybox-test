package com.moneybox.minimb.ui.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moneybox.minimb.R
import com.moneybox.minimb.data.networking.LocalAuthTokenRepository
import com.moneybox.minimb.data.networking.MoneyBoxApiService
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.data.networking.isFailure
import com.moneybox.minimb.data.networking.isRequesting
import com.moneybox.minimb.extensions.dataStore
import com.moneybox.minimb.extensions.doOnFailure
import com.moneybox.minimb.extensions.doOnSuccess
import com.moneybox.minimb.ui.common.CtaState
import com.moneybox.minimb.ui.common.EmptyString
import com.moneybox.minimb.ui.common.ResourceString
import com.moneybox.minimb.ui.login.data.RemoteLoginRepository
import com.moneybox.minimb.ui.login.domain.LoginInteractor
import com.moneybox.minimb.ui.login.domain.RemoteLoginInteractor
import com.moneybox.minimb.util.isValidEmail
import com.moneybox.minimb.util.isValidPassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class LoginViewModel(
    private val interactor: LoginInteractor
) : ViewModel() {

    private val initialState = LoginUiState(
        requestStatus = RequestStatus.DEFAULT,
        email = "",
        password = "",
        emailError = false,
        passwordError = false
    )

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _navigation = Channel<LoginNavEvent>(Channel.CONFLATED)
    val navigation = _navigation as ReceiveChannel<LoginNavEvent>

    fun onEmailUpdated(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = false
            )
        }
    }

    fun onPasswordUpdated(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = false
            )
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            val nextState = uiState.value.validateEmailAndPassword()
            _uiState.update { nextState }

            if (!nextState.emailError && !nextState.passwordError) {
                val state = uiState.value
                _uiState.update { it.toLoadingState() }
                interactor.loginWithEmailAndPassword(state.email, state.password)
                    .doOnSuccess {
                        _uiState.update { it.toSuccessState() }
                        _navigation.trySend(LoginNavEvent.NextScreen)
                    }
                    .doOnFailure { _uiState.update { it.toFailureState() } }
            }
        }
    }

    private fun LoginUiState.validateEmailAndPassword() = copy(
        emailError = !email.isValidEmail(),
        passwordError = !password.isValidPassword()
    )

    private fun LoginUiState.toLoadingState() = copy(
        requestStatus = RequestStatus.REQUESTING
    )

    private fun LoginUiState.toSuccessState() = copy(
        requestStatus = RequestStatus.SUCCESS
    )

    private fun LoginUiState.toFailureState() = copy(
        requestStatus = RequestStatus.FAILURE
    )
}

data class LoginUiState(
    @VisibleForTesting val requestStatus: RequestStatus,
    val email: String,
    val password: String,
    val emailError: Boolean,
    val passwordError: Boolean
) {

    val hasError = emailError || passwordError || requestStatus.isFailure()

    val errorMessage = when {
        emailError && passwordError -> ResourceString(R.string.email_and_password_error)
        emailError -> ResourceString(R.string.email_error)
        passwordError -> ResourceString(R.string.password_error)
        requestStatus.isFailure() -> ResourceString(R.string.problem_logging_in)
        else -> EmptyString
    }

    val ctaState = if (requestStatus.isRequesting()) CtaState.Loading
    else CtaState.Enabled(ResourceString(R.string.log_in))
}

sealed interface LoginNavEvent {
    object NextScreen : LoginNavEvent
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