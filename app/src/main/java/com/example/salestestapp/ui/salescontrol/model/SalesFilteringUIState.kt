package com.example.salestestapp.ui.salescontrol.model

import com.example.salestestapp.ui.salescontrol.enums.SalesSortFilter

data class SalesFilteringUIState(
    val sortingSalesBy: SalesSortFilter = SalesSortFilter.TIME,
    val voidSaleTypeChecked: Boolean = true,
    val validSaleTypeChecked: Boolean = true,
    val compSaleTypeChecked: Boolean = true,
    val missedSaleTypeChecked: Boolean = true,
    val wastedSaleTypeChecked: Boolean = true,
    val seatNumberSelected: String = "",
    val seatList: List<String> = emptyList()
)