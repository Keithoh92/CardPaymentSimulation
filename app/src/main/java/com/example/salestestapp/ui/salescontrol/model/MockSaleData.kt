package com.example.salestestapp.ui.salescontrol.model

import com.example.salestestapp.R
import com.example.salestestapp.ui.salescontrol.enums.SaleStatus
import org.joda.time.DateTime

val saleStatus = listOf(
    SaleStatus.VALID,
    SaleStatus.VOID,
    SaleStatus.VOID,
    SaleStatus.COMPLIMENTARY,
    SaleStatus.MISSED,
    SaleStatus.VALID,
    SaleStatus.VALID
)

fun mockSaleListItems(): List<SaleInfo> {
    val sales = mutableListOf<SaleInfo>()
    var timeStamp = 21.00
    val seats = listOf("1A", "1C", "2A", "3B", "3C", "4A", "4B")

    for (i in 0..6) {
        val updatedTimestamp = timeStamp.plus(i * 3)
        sales.add(
            SaleInfo(
                saleId = i,
                timestamp = updatedTimestamp.toString(),
                seatNumber = seats[i],
                changeCurrencyCode = "EUR",
                saleStatus = saleStatus[i],
                total = (10.00 % i).plus(2.35),
                crewCode = "123${i}"
            )
        )
    }
    return sales
}

fun mockSeatListState(): List<String> {
    val seatList = mutableListOf<String>()
    val salesList = mockSalesInfo()
    seatList.add("All")
    for (i in salesList.indices) {
        seatList.add(salesList[i].seatNumber!!)
    }

    return seatList
}

fun mockSalesInfo(): List<SaleInfoUIState> {
    val mockSaleList = mutableListOf<SaleInfoUIState>()

    val crewCodes = listOf("2252", "1234", "1123", "2252", "1123", "1234", "1123")
    val timestamps = listOf(
        DateTime.parse("2022-12-09T10:57").toDate(),
        DateTime.parse("2022-12-09T10:47").toDate(),
        DateTime.parse("2022-12-09T10:35").toDate(),
        DateTime.parse("2022-12-09T10:34").toDate(),
        DateTime.parse("2022-12-09T10:30").toDate(),
        DateTime.parse("2022-12-09T10:28").toDate(),
        DateTime.parse("2022-12-09T10:22").toDate(),
    )
    val totals = listOf(10.00, 20.20, 25.50, 3.25, 14.60, 9.30, 30.00)
    val seats = listOf("1A", "1C", "2A", "3B", "3C", "4A", "4B")
    val icons = listOf(
        R.drawable.icons8_contactless_64,
        R.drawable.icons8_contactless_64,
        R.drawable.icons8_cash_50,
        R.drawable.icons8_credit_card_50,
        R.drawable.icons8_star_32,
        R.drawable.icons8_voucher_80,
        R.drawable.icons8_contactless_64
    )

    for (i in 0..6) {
        mockSaleList.add(
            SaleInfoUIState(
                saleID = 1,
                salesAssistantCode = crewCodes[i],
                currencyCode = "EUR",
                saleStatus = saleStatus[i],
                seatNumber = seats[i],
                timestamp = timestamps[i],
                total = totals[i],
                paymentTypeIconResource = icons[i]
            )
        )
    }

    return mockSaleList
}