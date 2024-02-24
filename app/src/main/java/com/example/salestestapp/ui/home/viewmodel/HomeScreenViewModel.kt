package com.example.salestestapp.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.ui.home.effect.HomeScreenEffect
import com.example.salestestapp.ui.home.event.HomeScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel@Inject constructor(): ViewModel() {

    private val _effect = Channel<HomeScreenEffect>(Channel.UNLIMITED)
    val effect: Flow<HomeScreenEffect> = _effect.receiveAsFlow()

    fun onEvent(event: HomeScreenEvent) {
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
        _effect.send(destination)
    }
}