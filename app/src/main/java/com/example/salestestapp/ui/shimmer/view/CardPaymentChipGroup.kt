package com.example.salestestapp.ui.shimmer.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentChipGroup(
    modifier: Modifier = Modifier,
    buttonTitles: List<String> = listOf(),
    selectedIndex: Int? = 0,
    event: (CardPaymentScreenEvent) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing8)) {
            itemsIndexed(buttonTitles) { index, item ->

                CardPaymentChip(
                    text = item,
                    isSelected = selectedIndex == index,
                    onSelectionChanged = {
                        event(CardPaymentScreenEvent.OnSelectedChipChanged(index))
                    },
                )

            }
        }
    }
}

@Preview
@Composable
fun ChipGroupPreview() {
    AppTheme {
        CardPaymentChipGroup(buttonTitles =
        listOf("Payment Breakdown", "Card Signature"),
            event = {}
        )
    }
}