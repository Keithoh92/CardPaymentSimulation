package com.example.salestestapp.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.ui.compose.contract.ComposeContract
import com.example.salestestapp.ui.compose.contract.composeContractDelegate
import com.example.salestestapp.ui.home.effect.HomeScreenEffect
import com.example.salestestapp.ui.home.event.HomeScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
) : ViewModel(), ComposeContract<Unit, HomeScreenEvent, HomeScreenEffect>
    by composeContractDelegate(Unit) {

    override fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnCardPaymentSimulationClicked ->
                navigateTo(HomeScreenEffect.Navigation.CardPaymentScreen(false))
            is HomeScreenEvent.OnCardPaymentSignatureSimulationClicked ->
                navigateTo(HomeScreenEffect.Navigation.CardPaymentScreen(true))
            is HomeScreenEvent.OnSalesControlClicked ->
                navigateTo(HomeScreenEffect.Navigation.SalesControlScreen)
        }
    }

    private fun navigateTo(destination: HomeScreenEffect.Navigation) = viewModelScope.launch {
        emitEffect(destination)
    }
}