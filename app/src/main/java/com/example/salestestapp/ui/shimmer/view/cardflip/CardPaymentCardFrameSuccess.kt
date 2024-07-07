package com.example.salestestapp.ui.shimmer.view.cardflip

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.R
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.shimmer.model.CardDetails
import com.example.salestestapp.ui.theme.fontSize12
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.fontSize18
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.size80
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing24

@Composable
fun CardPaymentCardFrameSuccess(cardDetails: CardDetails) {
    val boxSize = with(LocalDensity.current) { 230.dp.toPx() }

    val gradientColorsCardDetails = if (!isSystemInDarkTheme()) {
        listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f)
        )
    } else {
        listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.5f)
        )
    }

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
                    .padding(start = spacing16, top = spacing24)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing16)
                        .height(50.dp)
                ) {
                    Text(
                        text = cardDetails.cardType.toString(),
                        fontSize = fontSize12,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.weight(full))

                    Image(
                        painter = painterResource(id = cardDetails.cardTypeSymbolResource),
                        contentDescription = null,
                        modifier = Modifier.size(size80),
                    )
                }
            }
            // Card Number
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing16)
            ) {
                Text(
                    text = cardDetails.getCardNumberFormatted(),
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace,
                    fontSize = fontSize18,
                    color = Color.Black
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
                Column() {
                    Text(
                        text = "CARD HOLDER",
                        fontSize = fontSize12,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black
                    )
                    Text(
                        text = cardDetails.cardHolder.uppercase(),
                        fontSize = fontSize16,
                        fontFamily = FontFamily.Default,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(full))
                // Expiry Date
                Column() {
                    Text(
                        text = "EXPIRY DATE",
                        fontSize = fontSize12,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black
                    )

                    Text(
                        text = cardDetails.expiryDate,
                        fontSize = fontSize16,
                        fontFamily = FontFamily.Default,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@ThemePreview
@Composable
fun CardPaymentCardFrameSuccessPreview() {
    val cardDetails = CardDetails(
        cardHolder = "Keith O'Hare",
        cardNumber = "1840932124567854",
        cardType = "DINERS",
        cardTypeSymbolResource = R.drawable.diners_logo,
        expiryDate = "08/25"
    )
    AppTheme { CardPaymentCardFrameSuccess(cardDetails) }
}