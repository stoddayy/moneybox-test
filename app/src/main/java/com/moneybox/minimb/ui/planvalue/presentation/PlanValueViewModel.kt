package com.moneybox.minimb.ui.planvalue.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moneybox.minimb.data.networking.RequestStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanValueViewModel(

) : ViewModel() {

    private val initialState = PlanValueUiState(
        requestStatus = RequestStatus.DEFAULT
    )

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

}

data class PlanValueUiState(
    val requestStatus: RequestStatus
)

val planValueViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    initializer {
        PlanValueViewModel(

        )
    }
}