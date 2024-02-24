package com.example.salestestapp.ui.salescontrol

import com.example.salestestapp.R

enum class SalesSortFilter(val salesSortingTitle: Int) {
    TIME(R.string.salescontrol_lblTime),
    SEAT_NUMBER(R.string.salescontrol_lblSeatNumber);

    companion object {
        @JvmStatic
        fun getByValue(salesTitleResource: Int): SalesSortFilter? {
            return values().firstOrNull { it.salesSortingTitle == salesTitleResource }
        }
    }
}