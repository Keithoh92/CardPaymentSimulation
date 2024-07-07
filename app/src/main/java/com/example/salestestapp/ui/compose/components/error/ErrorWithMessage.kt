package com.example.salestestapp.ui.compose.components.error

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.salestestapp.ui.theme.spacing24

@Composable
fun ErrorWithMessage(
    @RawRes lottieFile: Int,
    errorMessage: String,
    modifier: Modifier = Modifier,
    animationSize: Dp = 180.dp,
    onRetry: (() -> Unit)? = null
) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
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
            Text(
                text = errorMessage,
                modifier = Modifier.padding(horizontal = spacing24),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
            onRetry?.run {
                Spacer(modifier = Modifier.height(spacing24))
                OutlinedButton(onClick = this) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@ThemePreview
@Composable
private fun ErrorWithMessagePreview() {
    AppTheme {
        ErrorWithMessage(
            lottieFile = R.raw.error_anim,
            errorMessage = stringResource(id = R.string.something_went_wrong),
            modifier = Modifier.fillMaxSize()
        )
    }
}