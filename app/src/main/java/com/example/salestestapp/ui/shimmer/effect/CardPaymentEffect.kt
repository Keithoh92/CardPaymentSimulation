package com.example.testui.shimmer.effect

sealed class CardPaymentEffect {
    sealed class Toast(val messageResId: String): CardPaymentEffect() {
        object SignatureNotDrawnToast: Toast("No Signature drawn")
    }

    sealed class Navigation : CardPaymentEffect() {
        object Back: Navigation()
    }
}