package com.example.salestestapp.ui.salescontrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.salescontrol.effect.SalesControlEffect
import com.example.salestestapp.ui.salescontrol.enums.SaleStatus
import com.example.salestestapp.ui.salescontrol.enums.SalesFilterType
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.model.SaleInfoUIState
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.salescontrol.state.SalesFilteringBottomSheetUIState
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesControlViewModel@Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SalesListingUIState())
    val uiState: StateFlow<SalesListingUIState> = this._uiState.asStateFlow()

    private val _bottomSheetUIState = MutableStateFlow(SalesFilteringBottomSheetUIState())
    val bottomSheetUIState: StateFlow<SalesFilteringBottomSheetUIState> =
        this._bottomSheetUIState.asStateFlow()

    private val _effect = Channel<SalesControlEffect>(Channel.UNLIMITED)
    val effect: Flow<SalesControlEffect> = _effect.receiveAsFlow()

    private var salesAssistantFilter: String = FILTER_BY_ALL

    init {
        filterSales()
        populateSalesAssistantCodes()
    }

    private fun populateSalesAssistantCodes() {
        val listOfCrewCodes = mutableListOf<String>()
        listOfCrewCodes.add("All")
        mockSalesInfo().forEach {
            if (!listOfCrewCodes.contains(it.salesAssistantCode))
                listOfCrewCodes.add(it.salesAssistantCode)
        }
        _uiState.update { it.copy(salesAssistantCodes = listOfCrewCodes) }
    }

    fun onEvent(event: SalesControlScreenEvent) {
        when (event) {
            is SalesControlScreenEvent.OnSeatNumberSelected ->
                onChangeSeatNumberFilter(event.seatNumber)
            is SalesControlScreenEvent.OnSalesTypeChecked ->
                onSalesTypeChecked(event.type, event.checked)
            is SalesControlScreenEvent.OnCloseBottomSheet -> closeBottomSheet()
            is SalesControlScreenEvent.OnClickSortByOption -> onClickOrderChange(event.item)
            is SalesControlScreenEvent.OnClose -> navigateTo(SalesControlEffect.Navigation.OnBack)
            is SalesControlScreenEvent.OnClickMenuSalesFilterItem -> openCloseBottomSheet()
            is SalesControlScreenEvent.StopRefreshing ->
                _uiState.update { it.copy(isRefreshing = false) }
            is SalesControlScreenEvent.OnRefresh -> refreshSales()
            is SalesControlScreenEvent.OnResetClicked -> resetSales()
            is SalesControlScreenEvent.OnChangeCrewCodeFilter ->
                handleCrewFilterChanged(event.salesAssistantCode, event.index)
        }
    }

    private fun handleCrewFilterChanged(salesAssistantCode: String, selectedIndex: Int) {
        _uiState.update { it.copy(selectedSalesAssistantIndex = selectedIndex) }
        salesAssistantFilter = salesAssistantCode
        filterSales()
    }

    private fun refreshSales() {
        filterSales()
    }

    private fun resetSales() {
        _bottomSheetUIState.update { it.copy(seatNumberSelected = FILTER_BY_ALL) }
        resetSalesTypeFiltersToAll()
        _uiState.update { it.copy(selectedSalesAssistantIndex = 0) }
        salesAssistantFilter = FILTER_BY_ALL
        filterSales()
    }

    private fun navigateTo(destination: SalesControlEffect.Navigation) = viewModelScope.launch {
        _effect.send(destination)
    }

    private fun openCloseBottomSheet() = viewModelScope.launch {
        if (uiState.value.showBottomSheet) {
            _uiState.update { it.copy(showBottomSheet = false) }
        } else {
            _uiState.update { it.copy(showBottomSheet = true) }
        }
    }

    private fun closeBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = false) }
    }

    private fun updateSalesTypeFiltersCheckedState(filterBySaleStatusList: List<SaleStatus>) {
        if (allSalesTypeFiltersAreUnchecked(filterBySaleStatusList.size)) resetSalesTypeFiltersToAll()
    }

    private fun resetSalesTypeFiltersToAll() {
        _bottomSheetUIState.update {
            it.copy(
                isValidChecked = true,
                isVoidChecked = true,
                isMissedChecked = true,
                isCompChecked = true,
            )
        }
        filterSales()
        displayTheToast("Reset Sales")
    }

    private fun allSalesTypeFiltersAreUnchecked(filterBySaleStatusListSize: Int): Boolean = filterBySaleStatusListSize == 0

    private fun filterSales() = viewModelScope.launch {
        var filteredSales: List<SaleInfoUIState> = mockSalesInfo()
        val seatNumber = when (bottomSheetUIState.value.seatNumberSelected) {
            FILTER_BY_ALL -> null
            else -> bottomSheetUIState.value.seatNumberSelected
        }

        val salesAssistantCode = when (salesAssistantFilter) {
            FILTER_BY_ALL -> null
            else -> salesAssistantFilter
        }

        val filterBySaleStatusList = getSalesTypeFiltersChecked()
        updateSalesTypeFiltersCheckedState(filterBySaleStatusList)

        if (filterBySaleStatusList.isNotEmpty()) {
            filteredSales = filteredSales.filter { filterBySaleStatusList.contains(it.saleStatus) }
        }
        if (seatNumber != null) {
            filteredSales = filterBySeatNumber(filteredSales, seatNumber)
        }

        if (salesAssistantCode != null) {
            filteredSales = filterBySACode(filteredSales, salesAssistantCode)
        }


        updateSales(filteredSales)

        _uiState.update { it.copy(isRefreshing = false) }
    }

    private fun filterBySACode(
        filteredSales: List<SaleInfoUIState>,
        salesAssistantCode: String,
    ): List<SaleInfoUIState> {
        return filteredSales.filter { it.salesAssistantCode == salesAssistantCode }
    }

    private fun filterBySeatNumber(
        sales: List<SaleInfoUIState>,
        seatNumber: String,
    ) = sales.filter { it.seatNumber == seatNumber }

    private fun updateSales(listOfSales: List<SaleInfoUIState>) {
        _uiState.update { it.copy(salesLD = listOfSales) }
    }

    private fun getSalesTypeFiltersChecked(): List<SaleStatus> {
        val saleTypesChecked = mutableListOf<SaleStatus>()

        if (bottomSheetUIState.value.isValidChecked) saleTypesChecked.add(SaleStatus.VALID)
        if (bottomSheetUIState.value.isVoidChecked) saleTypesChecked.add(SaleStatus.VOID)
        if (bottomSheetUIState.value.isMissedChecked) saleTypesChecked.add(SaleStatus.MISSED)
        if (bottomSheetUIState.value.isCompChecked) saleTypesChecked.add(SaleStatus.COMPLIMENTARY)

        return saleTypesChecked
    }

    private fun displayTheToast(message: String) = viewModelScope.launch {
        _effect.send(SalesControlEffect.Toast(message))
    }

    private fun onSalesTypeChecked(salesTypes: SalesFilterType, isChecked: Boolean) = viewModelScope.launch {

        when (salesTypes) {
            SalesFilterType.FILTER_BY_COMP -> {
                _bottomSheetUIState.update { it.copy(isCompChecked = !isChecked) }
            }
            SalesFilterType.FILTER_BY_VALID -> {
                _bottomSheetUIState.update { it.copy(isValidChecked = !isChecked) }
            }
            SalesFilterType.FILTER_BY_VOID -> {
                _bottomSheetUIState.update { it.copy(isVoidChecked = !isChecked) }
            }
            else -> {
                _bottomSheetUIState.update { it.copy(isMissedChecked = !isChecked) }
            }
        }

        filterSales()
    }

    private fun onClickOrderChange(sortingOptionSelected: Int) {
        _uiState.update {
            it.copy(
                sortingBySelection =
                    SalesSortFilter.getByValue(sortingOptionSelected) ?: SalesSortFilter.TIME
            )
        }
        orderAndUpdateSalesList()
    }

    private fun orderAndUpdateSalesList() {
        if (uiState.value.sortingBySelection == SalesSortFilter.SEAT_NUMBER) orderSalesBySeatNumber()
        else orderSalesByTimeStamp()
    }

    private fun orderSalesByTimeStamp() {
        val comparator  = getTimeStampSortingComparator()
        _uiState.update { it.copy(salesLD = uiState.value.salesLD.sortedWith(comparator)) }
    }

    private fun getTimeStampSortingComparator(): Comparator<SaleInfoUIState> {
        return Comparator { s1: SaleInfoUIState, s2: SaleInfoUIState ->
            s1.timestamp.compareTo(s2.timestamp)
        }
    }

    private fun orderSalesBySeatNumber() {
        _uiState.update {
            it.copy(
                salesLD = uiState.value.salesLD.sortedWith(
                    SaleInfoUIState.seatNumberComparator(
                        "1A,1B,1C,1D,1E,2A,2B,2C,2D,2E,3A,3B,3C,3D,3E,4A,4B,4C,4D,4E"
                    )
                )
            )
        }
    }

    private fun onChangeSeatNumberFilter(seatNumber: String) {
        _bottomSheetUIState.update { it.copy(seatNumberSelected = seatNumber) }
        filterSales()
    }

    companion object FilterType {
        const val FILTER_BY_ALL = "All"
    }
}