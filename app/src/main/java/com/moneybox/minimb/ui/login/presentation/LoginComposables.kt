package com.moneybox.minimb.ui.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneybox.minimb.R
import com.moneybox.minimb.data.networking.RequestStatus
import com.moneybox.minimb.ui.common.MBButton
import com.moneybox.minimb.ui.common.MBTextField
import com.moneybox.minimb.ui.common.ResourceString
import com.moneybox.minimb.ui.common.emailKeyboardOptions
import com.moneybox.minimb.ui.common.passwordKeyboardOptions
import com.moneybox.minimb.ui.common.resolve
import com.moneybox.minimb.ui.theme.MoneyBoxTestTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val state by viewModel.uiState.collectAsState()

    MoneyBoxTestTheme {
        LoginContent(
            state = state,
            onEmailUpdated = viewModel::onEmailUpdated,
            onPasswordUpdated = viewModel::onPasswordUpdated,
            onLoginClicked = viewModel::onLoginClicked
        )
    }
}

@Composable
fun LoginContent(
    state: LoginUiState,
    onEmailUpdated: (String) -> Unit,
    onPasswordUpdated: (String) -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.moneybox_logo),
            contentDescription = "MoneyBox Logo",
            modifier = Modifier.padding(top = 48.dp)
        )

        MBTextField(
            modifier = Modifier
                .semantics { contentDescription = "Email Input" }
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            text = state.email,
            onTextValueUpdated = onEmailUpdated,
            labelText = ResourceString(R.string.email),
            keyboardOptions = emailKeyboardOptions,
            isError = state.emailError
        )

        MBTextField(
            modifier = Modifier
                .semantics { contentDescription = "Password Input" }
                .padding(16.dp),
            text = state.password,
            onTextValueUpdated = onPasswordUpdated,
            labelText = ResourceString(R.string.password),
            keyboardOptions = passwordKeyboardOptions,
            visualTransformation = PasswordVisualTransformation(mask = '*'),
            isError = state.passwordError
        )

        Spacer(modifier = Modifier.weight(1f))

        if (state.hasError) {
            Text(
                text = state.errorMessage.resolve(),
                modifier = Modifier
                    .semantics {contentDescription = "Login Error Message"}
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        MBButton(
            modifier = Modifier.padding(16.dp),
            ctaState = state.ctaState,
            onClick = onLoginClicked
        )
    }
}

@Composable
@Preview
private fun PreviewLoginContent() {
    LoginContent(
        state = LoginUiState(
            requestStatus = RequestStatus.DEFAULT,
            email = "michael@test.com",
            password = "Password",
            emailError = false,
            passwordError = false
        ),
        onEmailUpdated = {},
        onPasswordUpdated = {},
        onLoginClicked = {}
    )
}