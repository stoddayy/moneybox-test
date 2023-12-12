package com.moneybox.minimb.util

import java.util.regex.Pattern

private val emailRegex = Pattern.compile(
    ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+")
)

fun String.isValidEmail() = emailRegex.matcher(this).matches()

//Password strength rules currently unknown
fun String.isValidPassword() = length > 3