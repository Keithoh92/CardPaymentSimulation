package com.example.salestestapp.ui.salescontrol.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.spacing8

@SuppressLint("UnrememberedMutableState")
@Composable
fun SalesControlListView(
    uiState: SalesListingUIState,
    onEvent: (SalesControlScreenEvent) -> Unit
) {
    val state by mutableStateOf(uiState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing8)
    ) {
        Column(
            modifier = Modifier
                .border(
                    width = 0.5.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(5.dp)
                )
                .weight(full)
        ) {
            when {
                uiState.salesLD.isNotEmpty() -> {
                    Column {
                        SalesListSwipe(
                            salesInfoDisplayList = uiState.salesLD,
                            swipeEnabled = true
                        )
                    }
                }
                else -> EmptySaleListMessage(
                    currentSectorHasSales = true,
                    onResetClicked = { onEvent(SalesControlScreenEvent.OnResetClicked) }
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = spacing8))
        AnimatedVisibility(state.salesLD.isNotEmpty()) {
            val total = state.salesLD.sumOf { it.total }
            val formattedTotal = String.format("%.2f", total)
            SalesTotalCardView(
                totalSales = formattedTotal
            )
        }
    }
}

@ThemePreview
@Composable
fun SalesControlListViewPreview() {
    SalesControlListView(SalesListingUIState(), onEvent = {})
}