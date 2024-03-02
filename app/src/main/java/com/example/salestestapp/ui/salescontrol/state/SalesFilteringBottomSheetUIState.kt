package com.example.salestestapp.ui.salescontrol.state

import com.example.salestestapp.ui.salescontrol.enums.SalesSortFilter

data class SalesFilteringBottomSheetUIState(
    val sortingSalesBy: SalesSortFilter = SalesSortFilter.TIME,
    val isVoidChecked: Boolean = true,
    val isValidChecked: Boolean = true,
    val isMissedChecked: Boolean = true,
    val isCompChecked: Boolean = true,
    val seatNumberSelected: String = "All"
) {

    fun enableAllSalesTypeFilters(): SalesFilteringBottomSheetUIState {
        return this.copy(
            isValidChecked = true,
            isCompChecked = true,
            isMissedChecked = true,
            isVoidChecked = true
        )
    }
}