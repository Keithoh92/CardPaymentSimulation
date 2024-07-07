package com.example.salestestapp.ui.shimmer.view

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.compose.TopAppBar
import com.example.salestestapp.ui.compose.components.SalesErrorScreen
import com.example.salestestapp.ui.compose.components.SalesLoadingScreen
import com.example.salestestapp.ui.compose.contract.Compose
import com.example.salestestapp.ui.compose.contract.ErrorType
import com.example.salestestapp.ui.compose.contract.LoadingType
import com.example.salestestapp.ui.compose.contract.UIResult
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.state.CardPaymentScreenUIState
import com.example.salestestapp.ui.shimmer.view.cardflip.CardPaymentBottomButtons

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CardFaceDisplay(
    uiResult: UIResult<CardPaymentScreenUIState>,
    event: (CardPaymentScreenEvent) -> Unit,
) {
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = "Card Payment",
                    onBack = { event(CardPaymentScreenEvent.OnBack) }
                )
            },
            bottomBar = {
                CardPaymentBottomButtons(event)
            },
            content = { paddingValues ->
                uiResult.Compose(
                    onLoading = {
                        SalesLoadingScreen(loadingType = it)
                    },
                    onError = {
                        SalesErrorScreen(errorType = it, modifier = Modifier.fillMaxSize())
                    },
                    onLoaded = {
                        CardFaceContent(it, event, paddingValues)
                    }
                )
            }
        )
    }
    BackHandler(onBack = { event(CardPaymentScreenEvent.OnBack) })
}

@SuppressLint("UnrememberedMutableState")
@ThemePreview
@Composable
private fun CardFaceDisplayPreview(
    @PreviewParameter(CardFaceDisplayPreviewProvider::class)
    uiState: UIResult<CardPaymentScreenUIState>
) {
    AppTheme {
        CardFaceDisplay(
            uiResult = uiState,
            event = {}
        )
    }
}

private class CardFaceDisplayPreviewProvider :
    PreviewParameterProvider<UIResult<CardPaymentScreenUIState>> {
        val uiState = CardPaymentScreenUIState.initial()

    override val values: Sequence<UIResult<CardPaymentScreenUIState>>
        get() = sequenceOf(
            UIResult.Loading(LoadingType.WithTitle()),
            UIResult.Error(ErrorType.WithMessage()),
            UIResult.Loaded(uiState)
        )

}