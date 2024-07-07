package com.example.salestestapp.ui.common

import com.example.salestestapp.ui.salescontrol.event.SalesControlScreenEvent

sealed interface DialogType {
    data object None : DialogType
    data class Error(val message: String) : DialogType
    data class Loading(val message: String) : DialogType
    data class Warning(
        val message: String,
        val confirmEvent: SalesControlScreenEvent,
    ) : DialogType
}