package com.example.salestestapp.ui.home

import androidx.compose.runtime.Composable
import com.example.salestestapp.ui.compose.contract.CollectSideEffect
import com.example.salestestapp.ui.compose.contract.unpackWithUIResult
import com.example.salestestapp.ui.home.effect.HomeScreenEffect
import com.example.salestestapp.ui.home.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreenMain(
    viewModel: HomeScreenViewModel,
    onCard: (isSignature: Boolean) -> Unit,
    onSalesControl: () -> Unit,
    onBack: () -> Unit
) {
    val (_, event, effect) = viewModel.unpackWithUIResult()

    CollectSideEffect(sideEffect = effect) { effect ->
        when (effect) {
            is HomeScreenEffect.Navigation.CardPaymentScreen ->
                onCard.invoke(effect.isSignatureRequired)
            is HomeScreenEffect.Navigation.SalesControlScreen -> {
                onSalesControl.invoke()
            }
            is HomeScreenEffect.Navigation.Back -> onBack.invoke()
        }
    }

    HomeScreen(event)
}