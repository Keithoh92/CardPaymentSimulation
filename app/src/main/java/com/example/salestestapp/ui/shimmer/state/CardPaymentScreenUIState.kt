package com.example.salestestapp.ui.shimmer.state

import com.example.salestestapp.ui.shimmer.view.cardflip.model.CardFace
import com.example.salestestapp.ui.shimmer.model.CardDetails
import com.example.salestestapp.ui.shimmer.model.CardPaymentScreenAlertBannerConfig
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.shimmer.model.PaymentBreakdown
import com.example.salestestapp.ui.shimmer.model.SelectedOption

data class CardPaymentScreenUIState(
    val cardDetails: CardDetails,
    val paymentBreakdown: PaymentBreakdown,
    val acceptButtonEnabled: Boolean,
    val acceptAndPrintButtonEnabled: Boolean,
    val verifySignatureButtonEnabled: Boolean,
    val cardReadSuccessfully: Boolean,
    val paymentBreakdownCollapsed: Boolean,
    val cardSignatureCollapsed: Boolean,
    val paymentBreakdownIsShowing: Boolean,
    val cardSignatureIsShowing: Boolean,
    val selectedChipIndex: Int,
    val currentCardFace: CardFace,
    val cardFaceLoadingAndDetails: CardFace,
    val showAlertBanner: Boolean,
    val showBottomSheet: Boolean,

    val path: MutableList<CardSignatruePathState>,
    val canvasWidth: Int,
    val canvasHeight: Int,
    val applications: List<SelectedOption>,
    val showSelectionDialog: Boolean,
    val cardPaymentAlertBannerConfig: CardPaymentScreenAlertBannerConfig
) {
    companion object {
        fun initial() = CardPaymentScreenUIState(
            cardDetails = CardDetails(),
            paymentBreakdown = PaymentBreakdown(),
            acceptButtonEnabled = true,
            acceptAndPrintButtonEnabled = true,
            verifySignatureButtonEnabled = false,
            cardReadSuccessfully = false,
            paymentBreakdownCollapsed = true,
            cardSignatureCollapsed = false,
            paymentBreakdownIsShowing = true,
            cardSignatureIsShowing = false,
            selectedChipIndex = 0,
            currentCardFace = CardFace.Loading,
            cardFaceLoadingAndDetails = CardFace.Loading,
            showAlertBanner = true,
            showBottomSheet = false,
            path = mutableListOf(CardSignatruePathState()),
            canvasWidth = 0,
            canvasHeight = 0,
            applications = emptyList(),
            showSelectionDialog = false,
            cardPaymentAlertBannerConfig = CardPaymentScreenAlertBannerConfig()
        )
    }
}
