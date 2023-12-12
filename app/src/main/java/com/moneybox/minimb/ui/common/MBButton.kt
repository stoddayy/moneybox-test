package com.moneybox.minimb.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneybox.minimb.ui.theme.BlackBg

@Composable
fun MBButton(
    modifier: Modifier = Modifier,
    ctaState: CtaState,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = onClick,
        enabled = ctaState.ctaEnabled,
        shape = RoundedCornerShape(8.dp)
    ) {
        if (ctaState == CtaState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = BlackBg,
                strokeWidth = 2.dp
            )
        } else {
            Text(text = ctaState.ctaText.resolve().uppercase())
        }
    }
}