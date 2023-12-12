package com.moneybox.minimb.ui.login

import androidx.lifecycle.ViewModel
import com.moneybox.minimb.R
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.data.networking.isRequesting
import com.moneybox.minimb.ui.common.CtaState
import com.moneybox.minimb.ui.common.ResourceString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(

) : ViewModel() {

    private val initialState = LoginUiState(
        requestStatus = RequestStatus.DEFAULT
    )

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()




}

data class LoginUiState(
    private val requestStatus: RequestStatus
) {

    val ctaState = if(requestStatus.isRequesting()) CtaState.Loading
    else CtaState.Enabled(ResourceString(R.string.log_in))

}