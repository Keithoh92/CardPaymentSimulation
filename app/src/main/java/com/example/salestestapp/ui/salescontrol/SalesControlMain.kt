package com.example.salestestapp.ui.salescontrol

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.salestestapp.ui.salescontrol.effect.SalesControlEffect
import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SalesControlMain(viewModel: SalesControlViewModel, onBack: () -> Unit) {
    val context = LocalContext.current

    SalesControlScreen(
        salesControlUIState = viewModel.uiState,
        salesFilteringBottomSheetUIState = viewModel.bottomSheetUIState,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SalesControlEffect.Navigation.OnBack -> onBack.invoke()
                is SalesControlEffect.Toast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    })

}