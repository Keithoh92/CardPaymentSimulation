package com.example.salestestapp.ui.salescontrol.model

data class SaleInfo(
    val saleId: Int,
    val timestamp: String,
    val seatNumber: String,
    val changeCurrencyCode: String,
    val total: Double?,
    val crewCode: String,
)
