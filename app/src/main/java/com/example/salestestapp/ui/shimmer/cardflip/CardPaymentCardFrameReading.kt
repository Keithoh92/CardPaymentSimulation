package com.example.salestestapp.ui.shimmer.cardflip

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salestestapp.R
import com.example.salestestapp.ui.compose.ProgressIndicator
import com.example.salestestapp.ui.shimmer.model.CardDetails
import com.example.salestestapp.ui.theme.SalesTestAppTheme
import com.example.salestestapp.ui.theme.fontSize12
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.size12
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing32
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentCardFrameReading(cardDetails: CardDetails) {
    val gradientColors = listOf(
        Color.Gray.copy(alpha = 0.5f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.5f)
    )

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
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(spacing12)
        ) {
            // Card Type ----- Card Type Symbol
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing16)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(100.dp)
                        .height(spacing16)
                        .clip(RoundedCornerShape(spacing32))
                        .background(brush)
                )
            }
            // Card Number
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing8)
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

            // Card Holder Name & Exp date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing12, horizontal = spacing24)
            ) {
                // Card Holder name
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

                // Expiry Date
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

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CardPaymentCardFrameReadingPreview() {
    val cardDetails = CardDetails(
        cardHolder = "Keith O'Hare",
        cardNumber = "1840932124567854",
        cardType = "Mastercard",
        cardTypeSymbolResource = R.drawable.mastercard,
        expiryDate = "08/25"
    )
    SalesTestAppTheme {
        CardPaymentCardFrameReading(cardDetails)
    }
}