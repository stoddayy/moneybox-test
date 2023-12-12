package com.moneybox.minimb.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneybox.minimb.R
import com.moneybox.minimb.ui.theme.MoneyBoxTestTheme

@Composable
fun LoginScreen() {

    MoneyBoxTestTheme {
        LoginContent(

        )
    }
}

@Composable
fun LoginContent() {
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
    }
}