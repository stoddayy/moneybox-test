package com.moneybox.minimb.ui.planvalue.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneybox.minimb.R
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.ui.common.CtaState
import com.moneybox.minimb.ui.common.LoadingSpinner
import com.moneybox.minimb.ui.common.MBButton
import com.moneybox.minimb.ui.common.RawString
import com.moneybox.minimb.ui.common.ResourceString
import com.moneybox.minimb.ui.common.resolve
import com.moneybox.minimb.ui.theme.MoneyBoxTestTheme

@Composable
fun PlanValueScreen(
    viewModel: PlanValueViewModel
) {

    val state by viewModel.uiState.collectAsState()

    MoneyBoxTestTheme {
        PlanValueContent(
            state = state,
            onRetry = viewModel::refresh
        )
    }
}

@Composable
fun PlanValueContent(
    state: PlanValueUiState,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.requestStatus) {
            RequestStatus.DEFAULT -> Unit
            RequestStatus.REQUESTING -> LoadingSpinner()
            RequestStatus.SUCCESS -> TotalPlanValueSuccessContent(state = state)
            RequestStatus.FAILURE -> TotalPlanValueFailureContent(onRetry = onRetry)
        }
    }
}

@Composable
private fun TotalPlanValueSuccessContent(
    state: PlanValueUiState
) {
    Text(
        text = stringResource(id = R.string.total_plan_value),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 32.dp),
        color = MaterialTheme.colorScheme.onSurface
    )

    Text(
        text = state.planValue.resolve(),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 16.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TotalPlanValueFailureContent(
    onRetry: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.plan_value_error),
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.onSurface
    )

    MBButton(
        modifier = Modifier.padding(16.dp),
        ctaState = CtaState.Enabled(ResourceString(R.string.retry)),
        onClick = onRetry
    )
}

@Composable
@Preview
private fun PreviewPlanValueContent() {
    PlanValueContent(
        state = PlanValueUiState(
            requestStatus = RequestStatus.SUCCESS,
            planValue = RawString("£1,000.45")
        ),
        onRetry = {}
    )
}