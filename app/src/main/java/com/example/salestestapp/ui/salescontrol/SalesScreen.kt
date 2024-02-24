package com.example.salestestapp.ui.salescontrol

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salestestapp.ui.compose.TopAppBar
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.salescontrol.state.SalesFilteringBottomSheetUIState
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import com.example.salestestapp.ui.salescontrol.view.SalesControlButton
import com.example.salestestapp.ui.salescontrol.view.SalesControlListView
import com.example.salestestapp.ui.salescontrol.view.SalesFilteringBar
import com.example.salestestapp.ui.salescontrol.view.SalesFilteringBottomSheet
import com.example.salestestapp.ui.salescontrol.view.SalesScreenTitle
import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SalesControlScreen(
    salesControlUIState: StateFlow<SalesListingUIState>,
    salesFilteringBottomSheetUIState: StateFlow<SalesFilteringBottomSheetUIState>,
    onEvent: (SalesControlScreenEvent) -> Unit
) {
//    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val context = LocalContext.current

    val uiState by salesControlUIState.collectAsState()
    val bottomSheetUIState by salesFilteringBottomSheetUIState.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
//        drawerState = DrawerState(initialValue = DrawerValue.Closed),
        bottomSheetState = rememberBottomSheetState(
            initialValue = if (uiState.showBottomSheet) {
                BottomSheetValue.Expanded
            } else {
                BottomSheetValue.Collapsed
            }
        )
    )

    LaunchedEffect(key1 = uiState.showBottomSheet, block = {
        if (uiState.showBottomSheet) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        } else {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    })
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
        sheetElevation = 0.dp,
        sheetPeekHeight = 0.dp,
        topBar = { TopAppBar { onEvent(SalesControlScreenEvent.OnClose) } },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                SalesScreenTitle(
                    salesScreenTitleExtension = bottomSheetUIState.filtersChecked,
                    fromAirport = "DUB",
                    toAirport = "PAR"
                )
                SalesControlButton("Print Summary")

                SalesFilteringBar(
                    orderByTimeOrSeatNumberSelection = uiState.sortingBySelection,
                    filterMenuItemVisible = true,
                    isEnableSalesControlFilter = true,
                    onClickMenuSalesFilterItem = { onEvent(SalesControlScreenEvent.OnClickMenuSalesFilterItem) },
                    onClickSortByOption = { onEvent(SalesControlScreenEvent.OnClickSortByOption(it)) }
                )
                SalesControlListView()

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
    BackHandler(onBack = { onEvent(SalesControlScreenEvent.OnClose) })
}

@Preview(showBackground = true)
@Composable
fun SalesControlScreenPreview() {
    SalesControlScreen(
        salesControlUIState = mockUIState(),
        salesFilteringBottomSheetUIState = mockBottomSheetUIState(),
        onEvent = {}
    )
}

fun mockUIState(): StateFlow<SalesListingUIState> {
    return MutableStateFlow(
        SalesListingUIState(
            salesLD = mockSalesInfo(),
            isLoading = false,
            isRefreshing = false,
            totalSalesValue = 5.99,
            showBottomSheet = true
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
            seatNumberSelected = "1A",
            filtersChecked = SalesControlViewModel.FilterType.FILTER_BY_ALL
        )
    )
}