package com.example.salestestapp.ui.salescontrol.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AirlineSeatReclineNormal
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.salescontrol.model.SaleInfoUIState
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.fontSize18
import com.example.salestestapp.ui.theme.fontSize20
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.size24
import com.example.salestestapp.ui.theme.size32
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing8
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun SalesListItem(saleInfoUIState: SaleInfoUIState) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing8)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier
                .background(
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.outlineVariant
                    } else {
                        Color.LightGray.copy(alpha = 0.5f)
                    }
                )
                .padding(horizontal = spacing16, vertical = spacing8),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = spacing8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing8)
            ) {
                AnimatedVisibility(visible = saleInfoUIState.paymentTypeIconResource != null) {
                    saleInfoUIState.paymentTypeIconResource?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier.size(size32)
                        )
                    }
                }

                val tvTotal = "${saleInfoUIState.currencyCode} ${String.format("%.2f", saleInfoUIState.total)}"
                Text(
                    text = tvTotal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize18
                )

                Spacer(modifier = Modifier.weight(full))

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(size24)
                )

                Text(
                    text = saleInfoUIState.salesAssistantCode,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize16,
                )

                Icon(
                    imageVector = Icons.Outlined.AirlineSeatReclineNormal,
                    contentDescription = null,
                    modifier = Modifier.size(size24)
                )

                Text(
                    text = saleInfoUIState.seatNumber ?: "Seat Number Not Available",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize16,
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = spacing8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing8)
            ) {
                Text(
                    text = saleInfoUIState.saleStatus.toString(),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontSize = fontSize18,
                )

                Spacer(modifier = Modifier.weight(full))

                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = null,
                    modifier = Modifier.size(size24)
                )

                val formatter = SimpleDateFormat("HH:mm")
                val formattedDate = formatter.format(saleInfoUIState.timestamp)

                Text(
                    text = formattedDate,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize20,
                )
            }
        }
    }
}

@ThemePreview
@Composable
fun SalesListItemPreview() {
    AppTheme {
        SalesListItem(mockSalesInfo().first())
    }
}