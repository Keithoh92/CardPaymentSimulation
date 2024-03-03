package com.example.salestestapp.ui.salescontrol.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.fontSize14
import com.example.salestestapp.ui.theme.halfSpacing
import com.example.salestestapp.ui.theme.spacing24

@Composable
fun CrewCodesOutlinedButton(
    currentCrewCodeIndex: Int,
    selectedSalesAssistantCode: Int,
    saCode: String,
    onChangeSACodeFilter: (String, Int) -> Unit
) {
    OutlinedButton(
        modifier = Modifier.padding(halfSpacing).height(36.dp),
        onClick = {
            onChangeSACodeFilter(saCode, currentCrewCodeIndex)
        },
        shape = RoundedCornerShape(spacing24),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = if (currentCrewCodeIndex == selectedSalesAssistantCode) {
            ButtonDefaults.outlinedButtonColors(
                containerColor = Color.LightGray
            )
        } else {
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = saCode,
                color = if (currentCrewCodeIndex == selectedSalesAssistantCode) {
                    Color.Black
                } else {
                    Color.White
                },
                fontSize = fontSize14
            )
        }
    }
}

@ThemePreview
@Composable
fun CrewCodesOutlinedButtonPreview() {
    AppTheme {
        CrewCodesOutlinedButton(
            currentCrewCodeIndex = 1,
            selectedSalesAssistantCode = 1,
            saCode = "1234",
            onChangeSACodeFilter = { _, _ ->}
        )
    }
}