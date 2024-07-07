package com.example.salestestapp.ui.compose.components.loading

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.compose.AppTheme
import com.example.salestestapp.R
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.halfSpacing
import com.example.salestestapp.ui.theme.spacing24

@Composable
fun LoadingWithAnimation(
    @RawRes lottieFile: Int,
    modifier: Modifier = Modifier,
    title: String? = null,
    subTitle: String? = null,
    animationSize: Dp = 150.dp
) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(halfSpacing, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val lottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(lottieFile)
            )
            val progress by animateLottieCompositionAsState(
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever
            )
            LottieAnimation(
                composition = lottieComposition,
                progress = { progress },
                modifier = Modifier.size(animationSize)
            )
            title?.run {
                Text(
                    text = this,
                    modifier = Modifier.padding(horizontal = spacing24),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }
            subTitle?.run {
                Text(
                    text = this,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(horizontal = spacing24),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@ThemePreview
@Composable
private fun LoadingWithAnimationPreview(
    @PreviewParameter(LoadingWithAnimationPreviewProvider::class)
    titleMessagePair: Pair<String?, String?>
) {
    val (title, message) = titleMessagePair
    AppTheme {
        LoadingWithAnimation(
            lottieFile = R.raw.loading_anim,
            title = title,
            subTitle = message,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private class LoadingWithAnimationPreviewProvider :
    PreviewParameterProvider<Pair<String?, String?>> {
    override val values: Sequence<Pair<String?, String?>>
        get() = sequenceOf(
            Pair("Loading", "Loading all items"),
            Pair("Loading", null),
            Pair(null, "Loading all items"),
            Pair(null, null)
        )
}