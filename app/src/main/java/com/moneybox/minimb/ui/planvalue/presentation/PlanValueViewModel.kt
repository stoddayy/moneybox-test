package com.moneybox.minimb.ui.planvalue.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moneybox.minimb.data.networking.LocalAuthTokenRepository
import com.moneybox.minimb.data.networking.MoneyBoxApiService
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.extensions.dataStore
import com.moneybox.minimb.extensions.doOnFailure
import com.moneybox.minimb.extensions.doOnSuccess
import com.moneybox.minimb.ui.common.EmptyString
import com.moneybox.minimb.ui.common.TextProperty
import com.moneybox.minimb.ui.planvalue.data.RemotePlanValueRepository
import com.moneybox.minimb.ui.planvalue.domain.PlanValueInteractor
import com.moneybox.minimb.ui.planvalue.domain.RemotePlanValueInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlanValueViewModel(
    private val interactor: PlanValueInteractor
) : ViewModel() {

    private val initialState = PlanValueUiState(
        requestStatus = RequestStatus.DEFAULT,
        planValue = EmptyString
    )

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.toLoadingState() }
            interactor.retrieveUiPlanValue()
                .doOnSuccess { planValue ->
                    _uiState.update {
                        it.toSuccessState(planValue.planValue)
                    }
                }
                .doOnFailure { _uiState.update { it.toFailureState() } }
        }
    }

    private fun PlanValueUiState.toLoadingState() = copy(
        requestStatus = RequestStatus.REQUESTING
    )

    private fun PlanValueUiState.toSuccessState(planValue: TextProperty) = copy(
        requestStatus = RequestStatus.SUCCESS,
        planValue = planValue
    )

    private fun PlanValueUiState.toFailureState() = copy(
        requestStatus = RequestStatus.FAILURE
    )
}

data class PlanValueUiState(
    val requestStatus: RequestStatus,
    val planValue: TextProperty
)

fun planValueViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        PlanValueViewModel(
            interactor = RemotePlanValueInteractor(
                planValueRepository = RemotePlanValueRepository(
                    api = MoneyBoxApiService.instance
                ),
                authTokenRepository = LocalAuthTokenRepository(
                    dataStore = context.dataStore
                )
            )
        )
    }
}