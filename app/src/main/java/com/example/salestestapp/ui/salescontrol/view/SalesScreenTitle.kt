package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel
import com.example.salestestapp.ui.theme.fontSize24
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun SalesScreenTitle(
    salesScreenTitleExtension: String,
    fromAirport: String,
    toAirport: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing8, start = spacing8, bottom = spacing8)
    ) {
        Text(
            text = "Sales - ",
            fontSize = fontSize24,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            style = MaterialTheme.typography.h2
        )

        Text(
            text = "$fromAirport - $toAirport",
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SalesScreenTitlePreview() {
    SalesScreenTitle(
        salesScreenTitleExtension = SalesControlViewModel.FilterType.FILTER_BY_ALL,
        fromAirport = "DUB",
        toAirport = "PAR"
    )
}