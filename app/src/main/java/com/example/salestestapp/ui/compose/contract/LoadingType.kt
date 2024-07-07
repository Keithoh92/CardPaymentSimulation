package com.example.salestestapp.ui.compose.contract

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.example.salestestapp.R

interface LoadingType {

    data class WithTitle(
        @RawRes val lottieFile: Int = R.raw.loading_anim,
        @StringRes val title: Int = R.string.loading_please_wait
    ) : LoadingType

    data class WithTitleAndSubTitle(
        @RawRes val lottieFile: Int = R.raw.loading_anim,
        @StringRes val title: Int = R.string.loading_please_wait,
        @StringRes val subTitle: Int
    ) : LoadingType
}