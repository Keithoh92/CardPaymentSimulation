package com.example.salestestapp.ui.shimmer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.salestestapp.ui.theme.SalesTestAppTheme
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
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(halfSpacing)
        )
    }
) {
    Surface(
        elevation = spacing8,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray
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

@Preview(showBackground = true)
@Composable
fun ChipPreview(@PreviewParameter(ChipSelectedProvider::class) isSelected: Boolean) {
    SalesTestAppTheme() {
        CardPaymentChip(isSelected = isSelected)
    }
}

class ChipSelectedProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}