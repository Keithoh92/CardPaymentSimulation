package com.example.salestestapp.ui.salescontrol.model

import com.example.salestestapp.extensions.splitToSeatLetter
import com.example.salestestapp.extensions.splitToSeatNumber
import com.example.salestestapp.ui.salescontrol.enums.SaleStatus
import java.util.Date

data class SaleInfoUIState(
    val saleID: Int,
    val timestamp: Date,
    val currencyCode: String,
    val saleStatus: SaleStatus,
    val total: Double,
    val salesAssistantCode: String,
    val seatNumber: String?,
    val paymentTypeIconResource: Int?
) {
    companion object {
        fun seatNumberComparator(crewSeats: String): Comparator<SaleInfoUIState> {
            return compareBy(
                { it.seatNumber.splitToSeatNumber(crewSeats) },
                { it.seatNumber.splitToSeatLetter(crewSeats) })
        }
    }
}
