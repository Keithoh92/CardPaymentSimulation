package com.example.salestestapp.ui.salescontrol.effect

sealed class SalesControlEffect {
    sealed class Navigation : SalesControlEffect() {
        object OnBack : Navigation()
    }
}
