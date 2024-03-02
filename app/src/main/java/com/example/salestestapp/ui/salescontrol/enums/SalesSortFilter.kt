package com.example.salestestapp.ui.salescontrol.enums

import androidx.annotation.StringRes
import com.example.salestestapp.R

enum class SalesSortFilter(@StringRes val title: Int) {
    TIME(R.string.salescontrol_lblTime),
    SEAT_NUMBER(R.string.salescontrol_lblSeatNumber)
}