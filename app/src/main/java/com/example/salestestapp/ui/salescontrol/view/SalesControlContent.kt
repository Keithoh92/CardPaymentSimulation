package com.example.salestestapp.ui.salescontrol.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import kotlinx.coroutines.delay

@Composable
fun SalesControlContent(
    contentPadding: PaddingValues,
    uiState: SalesListingUIState,
    onEvent: (SalesControlScreenEvent) -> Unit,
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding())
    ) {
        SalesCurrentSectorRow(fromAirport = "DUB", toAirport = "PAR")

        SalesAssistantCodesSegmentedGroup(
            salesAssistantsCodes = uiState.salesAssistantCodes,
            selectedSACodeIndex = uiState.selectedSalesAssistantIndex,
            onChangeSACodeFilter = { saCode, index ->
                onEvent(SalesControlScreenEvent.OnChangeCrewCodeFilter(saCode, index))
            }
        )

        SalesFilteringBar(
            orderByTimeOrSeatNumberSelection = uiState.sortingBySelection,
            filterMenuItemVisible = true,
            isEnableSalesControlFilter = true,
            onClickMenuSalesFilterItem = {
                onEvent(SalesControlScreenEvent.OnClickMenuSalesFilterItem)
            },
            onClickSortByOption = {
                onEvent(SalesControlScreenEvent.OnClickSortByOption(it))
            },
            numberOfSales = uiState.salesLD.size
        )
        SalesControlListView(uiState, onEvent)

        if (uiState.isRefreshing) {
            Toast.makeText(context, "Sales Type Filter reset to ALL Sales", Toast.LENGTH_LONG).show()
            LaunchedEffect(key1 = true) {
                delay(3000)
                onEvent(SalesControlScreenEvent.StopRefreshing)
            }
        }
    }
}