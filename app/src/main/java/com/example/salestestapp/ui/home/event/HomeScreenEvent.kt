package com.example.salestestapp.ui.home.event

sealed class HomeScreenEvent {
    object OnCardPaymentSimulationClicked : HomeScreenEvent()
    object OnCardPaymentSignatureSimulationClicked : HomeScreenEvent()
    object OnSalesControlClicked : HomeScreenEvent()
}