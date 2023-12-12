package com.moneybox.minimb.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.moneybox.minimb.ui.theme.Aqua

@Composable
fun MBTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextValueUpdated: (String) -> Unit,
    labelText: TextProperty,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextValueUpdated,
        label = { Text(labelText.resolve()) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Aqua,
            unfocusedIndicatorColor = Aqua
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}

val emailKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)

val passwordKeyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)