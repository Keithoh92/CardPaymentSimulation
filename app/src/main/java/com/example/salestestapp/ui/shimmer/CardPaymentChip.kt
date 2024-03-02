package com.example.salestestapp.ui.shimmer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.halfSpacing
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentChip(
    onSelectionChanged: (String) -> Unit = {},
    text: String = "Chip",
    isSelected: Boolean = true,
    content: @Composable () -> Unit = {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(halfSpacing)
        )
    }
) {
    Surface(
        shadowElevation = spacing8,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(text)
                }
            )
        ) {
            content()
        }
    }
}

@ThemePreview
@Composable
fun ChipPreview(@PreviewParameter(ChipSelectedProvider::class) isSelected: Boolean) {
    AppTheme { CardPaymentChip(isSelected = isSelected) }
}

class ChipSelectedProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}