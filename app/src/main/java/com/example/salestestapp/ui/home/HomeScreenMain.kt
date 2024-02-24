package com.example.salestestapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.salestestapp.ui.home.effect.HomeScreenEffect
import com.example.salestestapp.ui.home.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenMain(
    viewModel: HomeScreenViewModel,
    onCard: (isSignature: Boolean) -> Unit,
    onSalesControl: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeScreenEffect.Navigation.CardPaymentScreen ->
                    onCard.invoke(effect.isSignatureRequired)
                is HomeScreenEffect.Navigation.SalesControlScreen -> {
                    onSalesControl.invoke()
                }
                is HomeScreenEffect.Navigation.Back -> onBack.invoke()
            }
        }
    })
    HomeScreen(viewModel::onEvent)
}