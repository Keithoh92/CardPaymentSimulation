package com.example.salestestapp.ui.salescontrol.effect

sealed class SalesControlEffect {
    data class Toast(val message: String) : SalesControlEffect()
    sealed class Navigation : SalesControlEffect() {
        object OnBack : Navigation()
    }
}
