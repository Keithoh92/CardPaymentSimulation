package com.example.salestestapp.ui.salescontrol.model

import com.example.salestestapp.ui.salescontrol.enums.SaleStatus

data class SaleInfo(
    val saleId: Int,
    val timestamp: String,
    val seatNumber: String,
    val saleStatus: SaleStatus,
    val changeCurrencyCode: String,
    val total: Double?,
    val crewCode: String,
)
