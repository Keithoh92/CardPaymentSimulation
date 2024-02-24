package com.example.salestestapp.ui.home.effect

sealed class HomeScreenEffect {

    sealed class Navigation: HomeScreenEffect() {
        data class CardPaymentScreen(val isSignatureRequired: Boolean) : Navigation()
        object SalesControlScreen : Navigation()
        object Back : Navigation()
    }
}