package com.example.salestestapp.ui.salescontrol.event

import com.example.salestestapp.ui.salescontrol.enums.SalesFilterType

sealed class SalesControlScreenEvent {
    object OnClose : SalesControlScreenEvent()
    object OnClickMenuSalesFilterItem : SalesControlScreenEvent()
    data class OnClickSortByOption(val item: Int) : SalesControlScreenEvent()
    data class OnSalesTypeChecked(val type: SalesFilterType, val checked: Boolean) : SalesControlScreenEvent()
    data class OnSeatNumberSelected(val seatNumber: String) : SalesControlScreenEvent()
    object OnCloseBottomSheet : SalesControlScreenEvent()
    object StopRefreshing : SalesControlScreenEvent()
    object OnRefresh : SalesControlScreenEvent()
    object OnResetClicked : SalesControlScreenEvent()
    data class OnChangeCrewCodeFilter(
        val salesAssistantCode: String,
        val index: Int,
    ) : SalesControlScreenEvent()
}
