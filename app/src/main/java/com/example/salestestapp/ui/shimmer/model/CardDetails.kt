package com.example.salestestapp.ui.shimmer.model

import com.example.salestestapp.R

data class CardDetails(
    val cardType: String = "CHINA UNION PAY",
    val cardTypeSymbolResource: Int = R.drawable.chine_union_pay_logo,
    val cardNumber: String = "1234542165216572",
    val cardHolder: String = "Keith O'Hare",
    val expiryDate: String = "08/25",
    val isChipAndSignature: Boolean = false
) {
    fun getCardNumberFormatted(): String {
        val cardNumberLength = cardNumber.length
        val first6 = cardNumber.substring(0, 6)
        val last4 = cardNumber.substring(cardNumberLength - 4, cardNumberLength)
        return "$first6 XXXX XXXX $last4"
    }
}
