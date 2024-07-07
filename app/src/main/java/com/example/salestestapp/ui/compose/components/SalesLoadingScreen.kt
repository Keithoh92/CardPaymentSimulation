package com.example.salestestapp.ui.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.compose.AppTheme
import com.example.salestestapp.R
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.compose.components.loading.LoadingWithAnimation
import com.example.salestestapp.ui.compose.contract.LoadingType

@Composable
fun SalesLoadingScreen(loadingType: LoadingType, modifier: Modifier = Modifier) {
    when (loadingType) {
        is LoadingType.WithTitle -> LoadingWithAnimation(
            lottieFile = loadingType.lottieFile,
            title = stringResource(id = loadingType.title),
            modifier = modifier
        )

        is LoadingType.WithTitleAndSubTitle -> LoadingWithAnimation(
            lottieFile = loadingType.lottieFile,
            title = stringResource(id = loadingType.title),
            subTitle = stringResource(id = loadingType.subTitle),
            modifier = modifier
        )
    }
}

@ThemePreview
@Composable
private fun VPosLoadingScreenPreview(
    @PreviewParameter(VPosLoadingScreenPreviewProvider::class) loadingType: LoadingType
) {
    AppTheme {
        SalesLoadingScreen(loadingType, modifier = Modifier.fillMaxSize())
    }
}

private class VPosLoadingScreenPreviewProvider :
    PreviewParameterProvider<LoadingType> {
    override val values: Sequence<LoadingType>
        get() = sequenceOf(
            LoadingType.WithTitle(),
            LoadingType.WithTitleAndSubTitle(subTitle = R.string.loading_please_wait)
        )
}