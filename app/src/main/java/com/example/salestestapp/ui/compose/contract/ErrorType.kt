package com.example.salestestapp.ui.compose.contract

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.example.salestestapp.R

sealed interface ErrorType {
    data class WithMessage(
        @RawRes val lottieFile: Int = R.raw.error_anim,
        @StringRes val message: Int = R.string.something_went_wrong
    ) : ErrorType
    data class WithMessageAndRetry(
        @RawRes val lottieFile: Int = R.raw.error_anim,
        @StringRes val message: Int = R.string.something_went_wrong,
        val retryAction: () -> Unit
    ) : ErrorType
}