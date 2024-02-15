package com.example.salestestapp.ui.shimmer.state

import com.example.salestestapp.ui.shimmer.cardflip.model.CardFace
import com.example.salestestapp.ui.shimmer.model.CardDetails
import com.example.salestestapp.ui.shimmer.model.CardPaymentScreenAlertBannerConfig
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.shimmer.model.PaymentBreakdown
import com.example.salestestapp.ui.shimmer.model.SelectedOption

data class CardPaymentScreenUIState constructor(
    val cardDetails: CardDetails = CardDetails(),
    val paymentBreakdown: PaymentBreakdown = PaymentBreakdown(),
    val acceptButtonEnabled: Boolean = true,
    val acceptAndPrintButtonEnabled: Boolean = true,
    val verifySignatureButtonEnabled: Boolean = false,
    val cardReadSuccessfully: Boolean = false,
    val paymentBreakdownCollapsed: Boolean = true,
    val cardSignatureCollapsed: Boolean = false,
    val paymentBreakdownIsShowing: Boolean = true,
    val cardSignatureIsShowing: Boolean = false,
    val selectedChipIndex: Int = 0,
    val currentCardFace: CardFace = CardFace.Loading,
    val cardFaceLoadingAndDetails: CardFace = CardFace.Loading,
    val showAlertBanner: Boolean = true,
    val showBottomSheet: Boolean = false,

    val path: MutableList<CardSignatruePathState> = mutableListOf(CardSignatruePathState()),
    val canvasWidth: Int = 0,
    val canvasHeight: Int = 0,
    val applications: List<SelectedOption> = emptyList(),
    val showSelectionDialog: Boolean = false,
    val cardPaymentAlertBannerConfig: CardPaymentScreenAlertBannerConfig = CardPaymentScreenAlertBannerConfig()
)
