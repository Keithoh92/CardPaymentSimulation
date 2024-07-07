package com.example.salestestapp.ui.salescontrol

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.common.DialogType
import com.example.salestestapp.ui.compose.TopAppBar
import com.example.salestestapp.ui.compose.components.SalesErrorScreen
import com.example.salestestapp.ui.compose.components.SalesLoadingScreen
import com.example.salestestapp.ui.compose.contract.Compose
import com.example.salestestapp.ui.compose.contract.ErrorType
import com.example.salestestapp.ui.compose.contract.LoadingType
import com.example.salestestapp.ui.compose.contract.UIResult
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.salescontrol.state.SalesFilteringBottomSheetUIState
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import com.example.salestestapp.ui.salescontrol.view.SalesControlContent
import com.example.salestestapp.ui.salescontrol.view.SalesFilteringBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesControlScreen(
    uiResult: UIResult<SalesListingUIState>,
    onEvent: (SalesControlScreenEvent) -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = if ((uiResult as? UIResult.Loaded)?.uiState?.showBottomSheet == true) {
                SheetValue.Expanded
            } else {
                SheetValue.Hidden
            },
            skipHiddenState = false
        )
    )

    LaunchedEffect(key1 = (uiResult as? UIResult.Loaded)?.uiState?.showBottomSheet, block = {
        if ((uiResult as? UIResult.Loaded)?.uiState?.showBottomSheet == true) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        } else {
            bottomSheetScaffoldState.bottomSheetState.hide()
        }
    })
    AppTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                (uiResult as? UIResult.Loaded)?.uiState?.salesFilteringBottomSheetUIState?.run {
                    SalesFilteringBottomSheet(
                        salesFilteringBottomSheetUIState = this,
                        onEvent = onEvent
                    )
                }
            },
            sheetShape = RoundedCornerShape(
                topStartPercent = 5,
                topEndPercent = 5),
            sheetPeekHeight = 0.dp,
            sheetSwipeEnabled = false,
            topBar = { TopAppBar(title = "Sales Control") { onEvent(SalesControlScreenEvent.OnClose) } },
            content = { paddingValues ->
                uiResult.Compose(
                    onLoading = {
                        SalesLoadingScreen(loadingType = it, modifier = Modifier.fillMaxSize())
                    },
                    onError = {
                        SalesErrorScreen(errorType = it, modifier = Modifier.fillMaxSize())
                    },
                    onLoaded = {
                        SalesControlContent(paddingValues, uiState = it, onEvent = onEvent)
                    }
                )
            }
        )
    }

    BackHandler(onBack = { onEvent(SalesControlScreenEvent.OnClose) })
}

@ThemePreview
@Composable
fun SalesControlScreenPreview(
    @PreviewParameter(SalesScreenPreviewProvider::class) uiState: UIResult<SalesListingUIState>
) {
    AppTheme { SalesControlScreen(uiResult = uiState, onEvent = {}) }
}

private class SalesScreenPreviewProvider : PreviewParameterProvider<UIResult<SalesListingUIState>> {
    val defaultState = SalesListingUIState(
        salesLD = mockSalesInfo(),
        isLoading = false,
        isRefreshing = false,
        totalSalesValue = 5.99,
        showBottomSheet = false,
        selectedSalesAssistantIndex = 1,
        salesAssistantCodes = listOf("All", "1234", "4231"),
        salesFilteringBottomSheetUIState = SalesFilteringBottomSheetUIState.initial(),
        dialogType = DialogType.None,
        sortingBySelection = SalesSortFilter.TIME
    )
    private val uiState = defaultState

    override val values: Sequence<UIResult<SalesListingUIState>>
        get() = sequenceOf(
            UIResult.Loading(LoadingType.WithTitle()),
            UIResult.Error(ErrorType.WithMessage()),
            UIResult.Loaded(uiState)
        )
}