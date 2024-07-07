package com.example.salestestapp.ui.shimmer.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentBreakdown() {
    Card(
        shape = RoundedCornerShape(spacing16),
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = if (!isSystemInDarkTheme()) Color.Black else Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.surface
        ),
        border = if (!isSystemInDarkTheme()) {
            BorderStroke(width = 1.dp, Color.LightGray.copy(alpha = 0.5f))
        } else {
            null
        },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing12)
        ) {
            PaymentBreakdownTextField(label = "Amount", value = "20.00")
            PaymentBreakdownTextField(label = "Total", value = "20.00")
        }
    }
}

@Composable
fun PaymentBreakdownTextField(
    label: String,
    value: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = fontSize16)
        Spacer(modifier = Modifier.weight(full))
        Text(text = value, fontSize = fontSize16)
    }
}

@ThemePreview
@Composable
fun CardPaymentBreakdownPreview() {
    AppTheme { CardPaymentBreakdown() }
}