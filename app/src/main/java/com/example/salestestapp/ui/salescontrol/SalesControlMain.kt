package com.example.salestestapp.ui.salescontrol

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.salestestapp.ui.compose.contract.CollectSideEffect
import com.example.salestestapp.ui.compose.contract.unpackWithUIResult
import com.example.salestestapp.ui.salescontrol.effect.SalesControlEffect
import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel

@Composable
fun SalesControlMain(viewModel: SalesControlViewModel, onBack: () -> Unit) {
    val context = LocalContext.current

    val (uiResult, onEvent, effect) = viewModel.unpackWithUIResult()

    SalesControlScreen(
        uiResult = uiResult,
        onEvent = onEvent
    )

    CollectSideEffect(sideEffect = effect) {
        when (it) {
            is SalesControlEffect.Navigation.OnBack -> onBack.invoke()
            is SalesControlEffect.Toast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}