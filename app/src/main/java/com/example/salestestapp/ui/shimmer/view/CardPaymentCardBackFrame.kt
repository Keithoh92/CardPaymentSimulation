package com.example.salestestapp.ui.shimmer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.shimmer.PaintBody
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.spacing12

@Composable
fun CardPaymentCardBackFrame(
    path: MutableList<CardSignatruePathState>,
    event: (CardPaymentScreenEvent) -> Unit
) {
    val boxSize = with(LocalDensity.current) { 230.dp.toPx() }

    val gradientColorsCardDetails = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val brushStill = Brush.linearGradient(
        colors = gradientColorsCardDetails,
        start = Offset(0f, 0f), // top left corner
        end = Offset(boxSize, boxSize)
    )

    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = brushStill)
            .height(230.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color.Black)
                    .padding(top = 60.dp)
            )

            val surfaceWidth = remember { mutableStateOf(0) }
            val surfaceHeight = remember { mutableStateOf(0) }

            Surface(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        surfaceWidth.value = size.width
                        surfaceHeight.value = size.height
                    },
                color = Color.White
            ) {
                ScratchPad(
                    path,
                    surfaceWidth.value,
                    surfaceHeight.value,
                    event
                )

                Text(
                    text = "Please sign here",
                    color = Color.Black.copy(0.5f),
                    modifier = Modifier.padding(top = 120.dp, end = spacing12),
                    fontSize = fontSize16,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
fun ScratchPad(
    path: MutableList<CardSignatruePathState>,
    canvasWidth: Int,
    canvasHeight: Int,
    event: (CardPaymentScreenEvent) -> Unit
) {
    PaintBody(
        path = path,
        canvasWidth,
        canvasHeight,
        event = event
    )
}

@ThemePreview
@Composable
fun CardPaymentCardBackFramePreview() {
    AppTheme { CardPaymentCardBackFrame(mutableListOf()) {} }
}