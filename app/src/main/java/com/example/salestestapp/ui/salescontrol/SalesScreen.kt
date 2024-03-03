package com.example.salestestapp.ui.salescontrol

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.compose.TopAppBar
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.salescontrol.state.SalesFilteringBottomSheetUIState
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import com.example.salestestapp.ui.salescontrol.view.SalesAssistantCodesSegmentedGroup
import com.example.salestestapp.ui.salescontrol.view.SalesControlListView
import com.example.salestestapp.ui.salescontrol.view.SalesCurrentSectorRow
import com.example.salestestapp.ui.salescontrol.view.SalesFilteringBar
import com.example.salestestapp.ui.salescontrol.view.SalesFilteringBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesControlScreen(
    salesControlUIState: StateFlow<SalesListingUIState>,
    salesFilteringBottomSheetUIState: StateFlow<SalesFilteringBottomSheetUIState>,
    onEvent: (SalesControlScreenEvent) -> Unit
) {
    val context = LocalContext.current

    val uiState by salesControlUIState.collectAsState()
    val bottomSheetUIState by salesFilteringBottomSheetUIState.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = if (uiState.showBottomSheet) {
                SheetValue.Expanded
            } else {
                SheetValue.Hidden
            },
            skipHiddenState = false
        )
    )

    LaunchedEffect(key1 = uiState.showBottomSheet, block = {
        if (uiState.showBottomSheet) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        } else {
            bottomSheetScaffoldState.bottomSheetState.hide()
        }
    })
    AppTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                SalesFilteringBottomSheet(
                    salesFilteringBottomSheetUIState = bottomSheetUIState,
                    onEvent = onEvent
                )
            },
            sheetShape = RoundedCornerShape(
                topStartPercent = 5,
                topEndPercent = 5),
            sheetPeekHeight = 0.dp,
            sheetSwipeEnabled = false,
            topBar = { TopAppBar(title = "Sales Control") { onEvent(SalesControlScreenEvent.OnClose) } },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding())
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
        )
    }

    BackHandler(onBack = { onEvent(SalesControlScreenEvent.OnClose) })
}

@ThemePreview
@Composable
fun SalesControlScreenPreview() {
    AppTheme {
        SalesControlScreen(
            salesControlUIState = mockUIState(),
            salesFilteringBottomSheetUIState = mockBottomSheetUIState(),
            onEvent = {}
        )
    }
}

fun mockUIState(): StateFlow<SalesListingUIState> {
    return MutableStateFlow(
        SalesListingUIState(
            salesLD = mockSalesInfo(),
            isLoading = false,
            isRefreshing = false,
            totalSalesValue = 5.99,
            showBottomSheet = false,
            selectedSalesAssistantIndex = 1,
            salesAssistantCodes = listOf("All", "1234", "4231")
        )
    )
}

fun mockBottomSheetUIState(): StateFlow<SalesFilteringBottomSheetUIState> {
    return MutableStateFlow(
        SalesFilteringBottomSheetUIState(
            isVoidChecked = true,
            isValidChecked = true,
            isMissedChecked = true,
            isCompChecked = true,
            seatNumberSelected = "1A"
        )
    )
}