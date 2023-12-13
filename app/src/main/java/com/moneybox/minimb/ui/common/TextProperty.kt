package com.moneybox.minimb.ui.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

interface TextProperty {
    fun resolve(context: Context): CharSequence
}

data class ResourceString(@StringRes val stringRes: Int) : TextProperty {
    override fun resolve(context: Context): CharSequence = context.getString(stringRes)
}

data class RawString(val string: String) : TextProperty {
    override fun resolve(context: Context): CharSequence = string
}

object EmptyString : TextProperty {
    override fun resolve(context: Context): CharSequence = ""
}

@Composable
fun TextProperty.resolve() = this.resolve(LocalContext.current).toString()