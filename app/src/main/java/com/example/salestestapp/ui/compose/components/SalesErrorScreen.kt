package com.example.salestestapp.ui.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.compose.components.error.ErrorWithMessage
import com.example.salestestapp.ui.compose.contract.ErrorType

@Composable
fun SalesErrorScreen(errorType: ErrorType, modifier: Modifier = Modifier) {
    when (errorType) {
        is ErrorType.WithMessage -> ErrorWithMessage(
            lottieFile = errorType.lottieFile,
            errorMessage = stringResource(id = errorType.message),
            modifier = modifier
        )
        is ErrorType.WithMessageAndRetry -> ErrorWithMessage(
            lottieFile = errorType.lottieFile,
            errorMessage = stringResource(id = errorType.message),
            onRetry = errorType.retryAction,
            modifier = modifier
        )
    }
}

@ThemePreview
@Composable
private fun VPosErrorScreenPreview() {
    AppTheme {
        SalesErrorScreen(ErrorType.WithMessage(), modifier = Modifier.fillMaxSize())
    }
}