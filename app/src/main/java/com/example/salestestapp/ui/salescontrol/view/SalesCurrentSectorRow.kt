package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun SalesCurrentSectorRow(
    fromAirport: String,
    toAirport: String
) {
    HorizontalDivider(color = Color.DarkGray)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        if (!isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                        },
                        MaterialTheme.colorScheme.surface
                    ),
                    start = Offset(100f, 0f),
                    end = Offset(100f, 100f)
                )
            )
            .padding(vertical = spacing8)
    ) {
        Text(
            text = fromAirport,
            fontWeight = FontWeight.Medium,
            color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium
        )
        Image(
            imageVector = Icons.Outlined.FlightTakeoff,
            colorFilter = ColorFilter.tint(
                if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary
            ),
            contentDescription = "Flight icon"
        )
        Text(
            text = toAirport,
            fontWeight = FontWeight.Medium,
            color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@ThemePreview
@Composable
fun SalesCurrentSectorRowPreview() {
    AppTheme {
        SalesCurrentSectorRow(
            fromAirport = "DUB",
            toAirport = "PAR"
        )
    }
}