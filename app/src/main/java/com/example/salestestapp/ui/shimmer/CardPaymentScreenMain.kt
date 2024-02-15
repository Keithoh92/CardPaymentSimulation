package com.example.salestestapp.ui.shimmer

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.salestestapp.ui.shimmer.cardflip.CardFaceDisplay
import com.example.testui.shimmer.effect.CardPaymentEffect
import com.example.salestestapp.ui.shimmer.viewmodel.CardPaymentScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CardPaymentScreenMain(
    viewModel: CardPaymentScreenViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is CardPaymentEffect.Toast -> {
                    Toast.makeText(context, it.messageResId, Toast.LENGTH_LONG).show()
                }
                is CardPaymentEffect.Navigation -> Unit
            }
        }
    }

    CardFaceDisplay(cardPaymentUIState = viewModel.cardPaymentUIState, event = viewModel::onEvent)
}