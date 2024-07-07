package com.example.salestestapp.ui.shimmer

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.salestestapp.ui.compose.contract.CollectSideEffect
import com.example.salestestapp.ui.compose.contract.unpackWithUIResult
import com.example.salestestapp.ui.shimmer.view.CardFaceDisplay
import com.example.salestestapp.ui.shimmer.effect.CardPaymentEffect
import com.example.salestestapp.ui.shimmer.viewmodel.CardPaymentScreenViewModel

@Composable
fun CardPaymentScreenMain(
    viewModel: CardPaymentScreenViewModel,
    isSignature: Boolean,
    onBack: () -> Unit,
    onAcceptSignature: (bitmap: Bitmap) -> Unit
) {
    val context = LocalContext.current

    val (uiResult, event, effect) = viewModel.unpackWithUIResult()

    CollectSideEffect(sideEffect = effect) {
        when (it) {
            is CardPaymentEffect.Toast -> {
                Toast.makeText(context, it.messageResId, Toast.LENGTH_LONG).show()
            }
            is CardPaymentEffect.Navigation.Back -> onBack.invoke()
            is CardPaymentEffect.OnAcceptSignature -> { onAcceptSignature.invoke(it.bitmap) }
        }
    }
    LaunchedEffect(key1 = Unit) { viewModel.isSignature = isSignature }

    CardFaceDisplay(
        uiResult = uiResult,
        event = event
    )
}