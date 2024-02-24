package com.example.salestestapp.ui.salescontrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.ui.salescontrol.SalesSortFilter
import com.example.salestestapp.ui.salescontrol.effect.SalesControlEffect
import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent
import com.example.salestestapp.ui.salescontrol.state.SalesFilteringBottomSheetUIState
import com.example.salestestapp.ui.salescontrol.state.SalesListingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _displayToast = MutableSharedFlow<String>()
    val displayToast = _displayToast.asSharedFlow()

    object FilterType {
        const val FILTER_BY_ALL = "All"
        const val FILTER_BY_SALE = "Sales"
        const val FILTER_BY_MISSED = "Missed"
        const val FILTER_BY_VOID = "Void"
        const val FILTER_BY_VALID = "Valid"
        const val FILTER_BY_COMP = "Complimentary"
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
            is SalesControlScreenEvent.OnClickMenuSalesFilterItem -> openBottomSheet()
            is SalesControlScreenEvent.StopRefreshing ->
                _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    private fun navigateTo(destination: SalesControlEffect.Navigation) = viewModelScope.launch {
        _effect.send(destination)
    }

    private fun openBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = true) }
    }

    private fun closeBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = false) }
    }

    private fun updateSalesList() = viewModelScope.launch {
        val listOfSales = getSalesTypeFiltersChecked()
        when {
            listOfSales.isNotEmpty() -> {
                val listOfCheckedTypes: StringBuilder = StringBuilder()
                var separator = ""

                listOfSales.forEach {
                    listOfCheckedTypes.append(separator)
                    listOfCheckedTypes.append(it)
                    separator = ", "
                }
                _bottomSheetUIState.update { it.copy(filtersChecked = listOfCheckedTypes.toString()) }
            }
            else -> _bottomSheetUIState.update { it.copy(filtersChecked = FilterType.FILTER_BY_ALL) }
        }

        _uiState.update { it.copy(isRefreshing = false) }
    }

    private suspend fun getSalesTypeFiltersChecked(): List<String> {
        val saleTypesChecked = mutableListOf<String>()

        when {
            bottomSheetUIState.value.isValidChecked -> saleTypesChecked.add("VALID")
            bottomSheetUIState.value.isVoidChecked -> saleTypesChecked.add("VOID")
            bottomSheetUIState.value.isMissedChecked -> saleTypesChecked.add("MISSED")
            bottomSheetUIState.value.isCompChecked -> saleTypesChecked.add("COMPLIMENTARY")
        }
        if (saleTypesChecked.isEmpty())
            enableAllSalesTypeFilters()

        return saleTypesChecked
    }

    private fun enableAllSalesTypeFilters() {
        _bottomSheetUIState.update { it.enableAllSalesTypeFilters() }

        displayTheToast("Sales Type Filter reset to ALL Sales")
    }

    private fun displayTheToast(message: String) = viewModelScope.launch {
        _displayToast.emit(message)
    }


    private fun onSalesTypeChecked(salesTypes: String, isChecked: Boolean) = viewModelScope.launch {

        when (salesTypes) {
            FilterType.FILTER_BY_COMP -> {
                _bottomSheetUIState.update { it.copy(isCompChecked = !isChecked) }
            }
            FilterType.FILTER_BY_VALID -> {
                _bottomSheetUIState.update { it.copy(isValidChecked = !isChecked) }
            }
            FilterType.FILTER_BY_VOID -> {
                _bottomSheetUIState.update { it.copy(isVoidChecked = !isChecked) }
            }
            else -> {
                _bottomSheetUIState.update { it.copy(isMissedChecked = !isChecked) }
            }
        }

        updateSalesList()
    }

    private fun onClickOrderChange(sortingOptionSelected: Int) {
        _uiState.update {
            it.copy(
                sortingBySelection =
                    SalesSortFilter.getByValue(sortingOptionSelected) ?: SalesSortFilter.TIME
            )
        }
    }

    private fun onChangeSeatNumberFilter(seatNumber: String) {
        _bottomSheetUIState.update { it.copy(seatNumberSelected = seatNumber) }
    }
}