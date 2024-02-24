package com.example.salestestapp.ui.salescontrol.model

fun mockSalesInfo(): List<SaleInfo> {
    val testSalesList = mutableListOf<SaleInfo>()

    val seatLetters = listOf("A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",)

    for (i in 0..10) {
        testSalesList.add(
            SaleInfo(
                saleId = 1,
                crewCode = "2022",
                changeCurrencyCode = "EUR",
                seatNumber = "${i}${seatLetters[i]}",
                timestamp = "21:05",
                total = 10.00
            )
        )
    }

    return testSalesList
}

fun mockSeatListState(): List<String> {
    val seatList = mutableListOf<String>()
    val salesList = mockSalesInfo()

    for (i in salesList.indices) {
        seatList.add(salesList[i].seatNumber)
    }

    return seatList
}