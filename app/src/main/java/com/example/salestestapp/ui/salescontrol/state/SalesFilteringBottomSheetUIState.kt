package com.example.salestestapp.ui.salescontrol.state

import com.example.salestestapp.ui.salescontrol.enums.SalesSortFilter

data class SalesFilteringBottomSheetUIState(
    val sortingSalesBy: SalesSortFilter = SalesSortFilter.TIME,
    val isVoidChecked: Boolean,
    val isValidChecked: Boolean,
    val isMissedChecked: Boolean,
    val isCompChecked: Boolean,
    val seatNumberSelected: String
) {

    fun enableAllSalesTypeFilters(): SalesFilteringBottomSheetUIState {
        return this.copy(
            isValidChecked = true,
            isCompChecked = true,
            isMissedChecked = true,
            isVoidChecked = true
        )
    }

    companion object {
        fun initial() = SalesFilteringBottomSheetUIState(
            sortingSalesBy = SalesSortFilter.TIME,
            isVoidChecked = true,
            isValidChecked = true,
            isMissedChecked = true,
            isCompChecked = true,
            seatNumberSelected = "All"
        )
    }
}