package com.example.salestestapp.ui.salescontrol

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.salestestapp.ui.salescontrol.effect.SalesControlEffect
import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SalesControlMain(viewModel: SalesControlViewModel, onBack: () -> Unit) {

    SalesControlScreen(
        salesControlUIState = viewModel.uiState,
        salesFilteringBottomSheetUIState = viewModel.bottomSheetUIState,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SalesControlEffect.Navigation.OnBack -> onBack.invoke()
            }
        }
    })

}