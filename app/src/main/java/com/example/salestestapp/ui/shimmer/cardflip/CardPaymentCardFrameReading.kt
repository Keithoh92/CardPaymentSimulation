package com.example.salestestapp.ui.shimmer.cardflip

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.compose.ProgressIndicator
import com.example.salestestapp.ui.theme.fontSize12
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.size12
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing32

@Composable
fun CardPaymentCardFrameReading() {
    val gradientColors = if (!isSystemInDarkTheme()) {
        listOf(
            Color.Gray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.Gray.copy(alpha = 0.5f)
        )
    } else {
        listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.Gray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.5f)
        )
    }

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(0f, 0f), // top left corner
        end = Offset(translateAnim.value, translateAnim.value)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(brush = brush),
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(spacing12)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing16, end = spacing16, top = spacing32)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(100.dp)
                        .height(spacing16)
                        .clip(RoundedCornerShape(spacing32))
                        .background(brush)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing32)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing32)
                        .size(spacing24)
                        .clip(RoundedCornerShape(spacing32))
                        .background(brush),
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(brush)
                        ) {
                            ProgressIndicator(size12)
                            Spacer(modifier = Modifier.width(spacing12))
                            Text(
                                text = "Waiting to read card details",
                                fontWeight = FontWeight.Light,
                                fontFamily = FontFamily.Monospace,
                                fontSize = fontSize12
                            )
                        }
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing16, horizontal = spacing24)
            ) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .width(100.dp)
                            .height(spacing16)
                            .clip(RoundedCornerShape(spacing32))
                            .background(brush)
                    )
                }

                Spacer(modifier = Modifier.weight(full))

                Column {
                    Spacer(
                        modifier = Modifier
                            .width(100.dp)
                            .height(spacing16)
                            .clip(RoundedCornerShape(spacing32))
                            .background(brush)
                    )
                }
            }
        }
    }
}

@ThemePreview
@Composable
fun CardPaymentCardFrameReadingPreview() {
    AppTheme { CardPaymentCardFrameReading() }
}