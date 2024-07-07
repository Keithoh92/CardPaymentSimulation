package com.example.salestestapp.ui.compose.contract

import androidx.compose.runtime.Composable

sealed class UIResult<out T> {
    data class Empty(val emptyType: EmptyType) : UIResult<Nothing>()
    data class Error(val errorType: ErrorType) : UIResult<Nothing>()
    data class Loading(val loadingType: LoadingType) : UIResult<Nothing>()
    data class Loaded<T>(val uiState: T) : UIResult<T>()
}

@Composable
fun<T> UIResult<T>.Compose(
    onLoading: (@Composable (LoadingType) -> Unit)? = null,
    onLoaded: @Composable (T) -> Unit,
    onEmpty: (@Composable (EmptyType) -> Unit)? = null,
    onError: (@Composable (ErrorType) -> Unit)? = null,
) {
    when (this) {
        is UIResult.Empty -> onEmpty?.invoke(this.emptyType)
        is UIResult.Error -> onError?.invoke(this.errorType)
        is UIResult.Loading -> onLoading?.invoke(this.loadingType)
        is UIResult.Loaded -> onLoaded(this.uiState)
    }
}