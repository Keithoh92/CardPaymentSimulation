package com.example.salestestapp.ui.shimmer.cardflip

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.ui.shimmer.cardflip.model.CardFace
import com.example.salestestapp.ui.theme.spacing32

@Composable
fun <T : Enum<T>> CardPaymentCardFrameFlip(
    cardFace: T,
    getCardAngle: (T) -> Float,
    isChipAndSignature: Boolean,
    modifier: Modifier = Modifier,
    front: @Composable () -> Unit = {},
    loading: @Composable () -> Unit = {},
    signature: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = getCardAngle(cardFace),
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ), label = ""
    )

    Card(
        onClick = {},
        modifier = modifier
            .clip(RoundedCornerShape(spacing32))
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) { loading() }
        } else if (rotation.value > 90f && rotation.value <= 270f) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .graphicsLayer { rotationY = 180f },
            ) {
                if (isChipAndSignature) { signature() } else { front() }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .graphicsLayer {
                        rotationY = 360f
                    },
            ) { front() }
        }
    }
}

@Preview
@Composable
private fun CardPaymentCardFrameFlipPreview() {
    AppTheme {
        CardPaymentCardFrameFlip(
            cardFace = CardFace.Front,
            getCardAngle = { cardFace -> cardFace.angle },
            isChipAndSignature = false
        )
    }
}