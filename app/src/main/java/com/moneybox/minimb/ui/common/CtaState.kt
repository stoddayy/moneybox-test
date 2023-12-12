package com.moneybox.minimb.ui.common

import android.view.View

sealed class CtaState(
    open val ctaText: TextProperty,
    val ctaEnabled: Boolean,
) {
    data class Enabled(override val ctaText: TextProperty) : CtaState(ctaText, true)
    data class Disabled(override val ctaText: TextProperty) : CtaState(ctaText, false)
    object Loading : CtaState(EmptyString, false)
}
