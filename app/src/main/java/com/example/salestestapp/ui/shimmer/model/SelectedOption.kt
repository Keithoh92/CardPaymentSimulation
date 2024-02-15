package com.example.salestestapp.ui.shimmer.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SelectedOption(val option: String, var initialSelectedValue: Boolean) {
    var selected by mutableStateOf(initialSelectedValue)
}