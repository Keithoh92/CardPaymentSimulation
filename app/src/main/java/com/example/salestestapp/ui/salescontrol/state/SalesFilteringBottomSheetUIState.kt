package com.example.salestestapp.ui.salescontrol.state

import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel

data class SalesFilteringBottomSheetUIState(
    val isVoidChecked: Boolean = true,
    val isValidChecked: Boolean = true,
    val isMissedChecked: Boolean = true,
    val isCompChecked: Boolean = true,
    val seatNumberSelected: String = "All",
    val filtersChecked: String = SalesControlViewModel.FilterType.FILTER_BY_ALL
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