package com.moneybox.minimb.ui.planvalue.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.moneybox.minimb.ui.theme.MoneyBoxTestTheme

@Composable
fun PlanValueScreen(
    viewModel: PlanValueViewModel
) {

    val state by viewModel.uiState.collectAsState()

    MoneyBoxTestTheme {
        PlanValueContent(
            state = state
        )
    }
}

@Composable
fun PlanValueContent(
    state: PlanValueUiState
) {

}