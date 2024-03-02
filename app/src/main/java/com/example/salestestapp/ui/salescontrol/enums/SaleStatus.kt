package com.example.salestestapp.ui.salescontrol.enums

import java.lang.RuntimeException

enum class SaleStatus(val id: Int) {
    VALID(0), VOID(1), MISSED(2), COMPLIMENTARY(3);

    companion object {
        operator fun get(statusId: Int): SaleStatus {
            for (saleStatus in values()) {
                if (saleStatus.id == statusId) {
                    return saleStatus
                }
            }
            throw RuntimeException("There is no Sale Status with ID $statusId")
        }
    }
}