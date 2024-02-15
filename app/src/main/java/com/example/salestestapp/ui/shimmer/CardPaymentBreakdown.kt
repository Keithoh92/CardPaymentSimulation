package com.example.salestestapp.ui.shimmer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salestestapp.ui.theme.SalesTestAppTheme
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
        elevation = 10.dp
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier.fillMaxWidth().padding(spacing12)
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
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label, fontSize = fontSize16)

        Spacer(modifier = Modifier.weight(full))

        Text(text = value, fontSize = fontSize16)
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CardPaymentBreakdownPreview() {
    SalesTestAppTheme() {
        CardPaymentBreakdown()
    }
}