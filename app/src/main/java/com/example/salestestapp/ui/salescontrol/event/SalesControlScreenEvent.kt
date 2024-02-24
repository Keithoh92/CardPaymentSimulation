package com.example.salestestapp.ui.salescontrol.event

sealed class SalesControlScreenEvent {
    object OnClose : SalesControlScreenEvent()
    object OnClickMenuSalesFilterItem : SalesControlScreenEvent()
    data class OnClickSortByOption(val item: Int) : SalesControlScreenEvent()
    data class OnSalesTypeChecked(val type: String, val checked: Boolean) : SalesControlScreenEvent()
    data class OnSeatNumberSelected(val seatNumber: String) : SalesControlScreenEvent()
    object OnCloseBottomSheet : SalesControlScreenEvent()
    object StopRefreshing : SalesControlScreenEvent()
}
