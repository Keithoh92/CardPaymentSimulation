package com.example.salestestapp.ui.salescontrol.state

import com.example.salestestapp.ui.common.DialogType
import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.salescontrol.model.SaleInfoUIState
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo

data class SalesListingUIState(
    val salesLD: List<SaleInfoUIState>,
    val dialogType: DialogType,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val totalSalesValue: Double,
    val showBottomSheet: Boolean,
    val selectedSalesAssistantIndex: Int,
    val sortingBySelection: SalesSortFilter,
    val salesAssistantCodes: List<String>,
    val salesFilteringBottomSheetUIState: SalesFilteringBottomSheetUIState
) {
    companion object {
        fun initial() = SalesListingUIState(
            salesLD =  mockSalesInfo(),
            dialogType = DialogType.None,
            isLoading = false,
            isRefreshing = false,
            totalSalesValue = 0.00,
            showBottomSheet = false,
            selectedSalesAssistantIndex = 0,
            sortingBySelection = SalesSortFilter.SEAT_NUMBER,
            salesAssistantCodes = emptyList(),
            salesFilteringBottomSheetUIState = SalesFilteringBottomSheetUIState.initial()
        )
    }
}