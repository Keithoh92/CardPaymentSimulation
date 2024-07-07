package com.example.salestestapp.ui.salescontrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.R
import com.example.salestestapp.ui.common.DialogType
import com.example.salestestapp.ui.compose.contract.ComposeContract
import com.example.salestestapp.ui.compose.contract.LoadingType
import com.example.salestestapp.ui.compose.contract.composeContractDelegate
import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.salescontrol.effect.SalesControlEffect
import com.example.salestestapp.ui.salescontrol.enums.SaleStatus
import com.example.salestestapp.ui.salescontrol.enums.SalesFilterType
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.model.SaleInfoUIState
import com.example.salestestapp.ui.salescontrol.model.mockSalesInfo
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import com.example.salestestapp.util.StringResHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesControlViewModel @Inject constructor(
    private val stringResHelper: StringResHelper,
) : ViewModel(),
    ComposeContract<SalesListingUIState, SalesControlScreenEvent, SalesControlEffect>
    by composeContractDelegate(SalesListingUIState.initial()
) {

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
        setLoadedResult { copy(salesAssistantCodes = listOfCrewCodes) }
    }

    override fun onEvent(event: SalesControlScreenEvent) {
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
                setLoadedResult { copy(isRefreshing = false) }
            is SalesControlScreenEvent.OnRefresh -> refreshSales()
            is SalesControlScreenEvent.OnResetClicked -> resetSales()
            is SalesControlScreenEvent.OnChangeCrewCodeFilter ->
                handleCrewFilterChanged(event.salesAssistantCode, event.index)
        }
    }

    private fun handleCrewFilterChanged(salesAssistantCode: String, selectedIndex: Int) {
        setLoadedResult { copy(selectedSalesAssistantIndex = selectedIndex) }
        salesAssistantFilter = salesAssistantCode
        filterSales()
    }

    private fun refreshSales() {
        setLoadingResult(LoadingType.WithTitle())
        filterSales()
    }

    private fun resetSales() {
        setLoadedResult {
            copy(
                salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                    seatNumberSelected = FILTER_BY_ALL
                )
            )
        }
        resetSalesTypeFiltersToAll()
        setLoadedResult { copy(selectedSalesAssistantIndex = 0) }
        salesAssistantFilter = FILTER_BY_ALL
        filterSales()
    }

    private fun navigateTo(destination: SalesControlEffect.Navigation) = viewModelScope.launch {
        emitEffect(destination)
    }

    private fun openCloseBottomSheet() = viewModelScope.launch {
        if (uiState.value.showBottomSheet) {
            setLoadedResult { copy(showBottomSheet = false) }
        } else {
            setLoadedResult { copy(showBottomSheet = true) }
        }
    }

    private fun closeBottomSheet() {
        setLoadedResult { copy(showBottomSheet = false) }
    }

    private fun updateSalesTypeFiltersCheckedState(filterBySaleStatusList: List<SaleStatus>) {
        if (allSalesTypeFiltersAreUnchecked(filterBySaleStatusList.size)) resetSalesTypeFiltersToAll()
    }

    private fun resetSalesTypeFiltersToAll() {
        setLoadedResult {
            copy(
                salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                    isValidChecked = true,
                    isVoidChecked = true,
                    isMissedChecked = true,
                    isCompChecked = true,
                )
            )
        }
        filterSales()
        displayTheToast("Reset Sales")
    }

    private fun allSalesTypeFiltersAreUnchecked(filterBySaleStatusListSize: Int): Boolean = filterBySaleStatusListSize == 0

    private fun filterSales() = viewModelScope.launch {
        var filteredSales: List<SaleInfoUIState> = mockSalesInfo()
        val seatNumber = when (uiState.value.salesFilteringBottomSheetUIState.seatNumberSelected) {
            FILTER_BY_ALL -> null
            else -> uiState.value.salesFilteringBottomSheetUIState.seatNumberSelected
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

        setLoadedResult { copy(isRefreshing = false) }
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
        setLoadedResult { copy(salesLD = listOfSales) }
    }

    private fun getSalesTypeFiltersChecked(): List<SaleStatus> {
        val saleTypesChecked = mutableListOf<SaleStatus>()

        if (uiState.value.salesFilteringBottomSheetUIState.isValidChecked) {
            saleTypesChecked.add(SaleStatus.VALID)
        }
        if (uiState.value.salesFilteringBottomSheetUIState.isVoidChecked) {
            saleTypesChecked.add(SaleStatus.VOID)
        }
        if (uiState.value.salesFilteringBottomSheetUIState.isMissedChecked) {
            saleTypesChecked.add(SaleStatus.MISSED)
        }
        if (uiState.value.salesFilteringBottomSheetUIState.isCompChecked) {
            saleTypesChecked.add(SaleStatus.COMPLIMENTARY)
        }

        return saleTypesChecked
    }

    private fun displayTheToast(message: String) = viewModelScope.launch {
        emitEffect(SalesControlEffect.Toast(message))
    }

    private fun showLoadingDialog() {
        setLoadedResult {
            copy(
                dialogType = DialogType.Loading(
                    stringResHelper.getString(R.string.loading_please_wait)
                )
            )
        }
    }

    private fun onSalesTypeChecked(salesTypes: SalesFilterType, isChecked: Boolean) = viewModelScope.launch {

        when (salesTypes) {
            SalesFilterType.FILTER_BY_COMP -> {
                setLoadedResult {
                    copy(
                        salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                            isCompChecked = !isChecked
                        ),
                    )
                }
            }
            SalesFilterType.FILTER_BY_VALID -> {
                setLoadedResult {
                    copy(
                        salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                            isValidChecked = !isChecked
                        )
                    )
                }
            }
            SalesFilterType.FILTER_BY_VOID -> {
                setLoadedResult {
                    copy(
                        salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                            isVoidChecked = !isChecked
                        )
                    )
                }
            }
            else -> {
                setLoadedResult {
                    copy(
                        salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                            isMissedChecked = !isChecked
                        )
                    )
                }
            }
        }

        filterSales()
    }

    private fun onClickOrderChange(sortingOptionSelected: Int) {
        setLoadedResult {
            copy(
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
        setLoadedResult { copy(salesLD = uiState.value.salesLD.sortedWith(comparator)) }
    }

    private fun getTimeStampSortingComparator(): Comparator<SaleInfoUIState> {
        return Comparator { s1: SaleInfoUIState, s2: SaleInfoUIState ->
            s1.timestamp.compareTo(s2.timestamp)
        }
    }

    private fun orderSalesBySeatNumber() {
        setLoadedResult {
            copy(
                salesLD = uiState.value.salesLD.sortedWith(
                    SaleInfoUIState.seatNumberComparator(
                        "1A,1B,1C,1D,1E,2A,2B,2C,2D,2E,3A,3B,3C,3D,3E,4A,4B,4C,4D,4E"
                    )
                )
            )
        }
    }

    private fun onChangeSeatNumberFilter(seatNumber: String) {
        setLoadedResult {
            copy(
                salesFilteringBottomSheetUIState = salesFilteringBottomSheetUIState.copy(
                    seatNumberSelected = seatNumber
                )
            )
        }
        filterSales()
    }

    companion object FilterType {
        const val FILTER_BY_ALL = "All"
    }
}