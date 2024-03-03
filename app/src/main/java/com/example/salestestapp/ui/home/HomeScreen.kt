package com.example.salestestapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.home.event.HomeScreenEvent
import com.example.salestestapp.ui.theme.spacing32
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun HomeScreen(onEvent: (HomeScreenEvent) -> Unit) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing8),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = spacing32),
                text = "Credit Card Flip Prototype",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(HomeScreenEvent.OnCardPaymentSimulationClicked) }
            ) {
                Text(
                    text = "Payment Simulation",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(HomeScreenEvent.OnCardPaymentSignatureSimulationClicked) }
            ) {
                Text(
                    text = "Payment Signature Simulation",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                modifier = Modifier.padding(top = spacing32, bottom = spacing32),
                text = "Sales Control Prototype",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(HomeScreenEvent.OnSalesControlClicked) }
            ) {
                Text(
                    text = "Sales Control",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@ThemePreview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        Surface {
            HomeScreen(onEvent = {})
        }
    }
}