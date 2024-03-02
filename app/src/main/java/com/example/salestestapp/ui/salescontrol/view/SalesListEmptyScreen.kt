package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.R
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.fontSize20
import com.example.salestestapp.ui.theme.fontSize32
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun EmptySaleListMessage(
    currentSectorHasSales: Boolean,
    onResetClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sales_screen_no_sales),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(spacing8))

        if (currentSectorHasSales) {
            EmptySalesText("No Sales Found", "No sales found for the sales filters applied.")

            Spacer(modifier = Modifier.height(spacing16))

            OutlinedButton(
                onClick = { onResetClicked() },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF2091eb)
                ),
                modifier = Modifier.padding(spacing8)
            ) {
                Text(
                    text = "Reset Filters",
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.padding(start = spacing8)
                )
            }
        } else {
            EmptySalesText("No Sales Yet", "No sales on this handheld during the current sector.")
        }
    }
}

@Composable
fun EmptySalesText(title: String, message: String) {
    Text(
        text = title,
        fontSize = fontSize32,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(spacing16))

    Text(
        text = message,
        fontSize = fontSize20,
        color = Color.DarkGray,
        textAlign = TextAlign.Center
    )
}

@ThemePreview
@Composable
fun EmptyListViewMessagePreview() {
    AppTheme {
        EmptySaleListMessage(
            currentSectorHasSales = true,
            onResetClicked = {}
        )
    }
}