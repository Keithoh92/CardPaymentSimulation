package com.example.salestestapp.ui.shimmer.effect

import android.graphics.Bitmap

sealed class CardPaymentEffect {
    sealed class Toast(val messageResId: String): CardPaymentEffect() {
        object SignatureNotDrawnToast: Toast("No Signature drawn")
    }
    sealed class Navigation : CardPaymentEffect() {
        object Back: Navigation()
    }
    data class OnAcceptSignature(val bitmap: Bitmap) : CardPaymentEffect()
}