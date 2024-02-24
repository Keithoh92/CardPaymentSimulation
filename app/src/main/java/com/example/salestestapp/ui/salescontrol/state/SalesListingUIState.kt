package com.example.salestestapp.ui.salescontrol.state

import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.salescontrol.model.SaleInfo
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo

data class SalesListingUIState(
    val salesLD: List<SaleInfo> = mockSalesInfo(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val totalSalesValue: Double = 0.00,
    val showBottomSheet: Boolean = false,
    val sortingBySelection: SalesSortFilter = SalesSortFilter.TIME
)