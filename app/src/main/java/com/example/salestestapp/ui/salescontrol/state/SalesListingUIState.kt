package com.example.salestestapp.ui.salescontrol.state

import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.salescontrol.model.SaleInfoUIState
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo

data class SalesListingUIState(
    val salesLD: List<SaleInfoUIState> = mockSalesInfo(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val totalSalesValue: Double = 0.00,
    val showBottomSheet: Boolean = false,
    val selectedSalesAssistantIndex: Int = 0,
    val sortingBySelection: SalesSortFilter = SalesSortFilter.SEAT_NUMBER,
    val salesAssistantCodes: List<String> = emptyList()
)