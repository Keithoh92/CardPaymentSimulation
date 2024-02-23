package com.example.salestestapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.salestestapp.ui.home.effect.HomeScreenEffect
import com.example.salestestapp.ui.home.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenMain(
    viewModel: HomeScreenViewModel,
    onClick: (isSignature: Boolean) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeScreenEffect.Navigation.CardPaymentScreen ->
                    onClick.invoke(effect.isSignatureRequired)
                is HomeScreenEffect.Navigation.Back -> onBack.invoke()
            }
        }
    })
    HomeScreen(viewModel::onEvent)
}