package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.fontSize20
import com.example.salestestapp.ui.theme.halfSpacing
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun SalesTotalCardView(
    totalSales: String
) {
    AnimatedVisibility(totalSales.isNotEmpty()) {
        Card(
            colors = CardColors(
                containerColor = if (isSystemInDarkTheme()) {
                    Color.DarkGray
                } else {
                    Color.LightGray.copy(alpha = 0.5f)
                },
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing8, top = spacing8, bottom = spacing8)
            ) {
                SalesTotalCardTextView("Total")
                SalesTotalCardTextView(text = totalSales)
            }
        }
    }
}

@Composable
fun SalesTotalCardTextView(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = fontSize20,
        modifier = Modifier.padding(start = halfSpacing),
        color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary
    )
}

@ThemePreview
@Composable
fun SalesTotalCardViewPreview() {
    AppTheme {
        SalesTotalCardView(
            totalSales = "30.99"
        )
    }
}