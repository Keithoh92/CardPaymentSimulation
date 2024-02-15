package com.example.salestestapp.ui.shimmer.cardflip

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salestestapp.R
import com.example.salestestapp.ui.shimmer.model.CardDetails
import com.example.salestestapp.ui.theme.SalesTestAppTheme
import com.example.salestestapp.ui.theme.fontSize12
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.fontSize18
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.size100
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentCardFrameSuccess(cardDetails: CardDetails) {
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
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = brushStill)
            .height(230.dp),
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
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.weight(full))

                    Image(
                        painter = painterResource(id = cardDetails.cardTypeSymbolResource),
                        contentDescription = null,
                        modifier = Modifier.size(size100),
                    )
                }
            }
            // Card Number
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing8)
            ) {
                Text(
                    text = cardDetails.getCardNumberFormatted(),
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace,
                    fontSize = fontSize18
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
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = cardDetails.cardHolder.uppercase(),
                        fontSize = fontSize16,
                        fontFamily = FontFamily.Default
                    )
                }
                Spacer(modifier = Modifier.weight(full))
                // Expiry Date
                Column() {
                    Text(
                        text = "EXPIRY DATE",
                        fontSize = fontSize12,
                        fontFamily = FontFamily.SansSerif
                    )

                    Text(
                        text = cardDetails.expiryDate,
                        fontSize = fontSize16,
                        fontFamily = FontFamily.Default
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CardPaymentCardFrameSuccessPreview() {
    val cardDetails = CardDetails(
        cardHolder = "Keith O'Hare",
        cardNumber = "1840932124567854",
        cardType = "DINERS",
        cardTypeSymbolResource = R.drawable.diners_logo,
        expiryDate = "08/25"
    )
    SalesTestAppTheme() {
        CardPaymentCardFrameSuccess(cardDetails)
    }
}