package com.moneybox.minimb.extensions

import java.text.NumberFormat
import java.util.Locale

fun Float.formatAsCurrency(): String = NumberFormat.getCurrencyInstance(Locale.UK).format(this)
