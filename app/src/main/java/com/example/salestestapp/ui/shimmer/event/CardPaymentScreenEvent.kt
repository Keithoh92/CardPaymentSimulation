package com.example.salestestapp.ui.shimmer.event

import com.example.testui.common.BaseComposeEvent
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState

sealed interface CardPaymentScreenEvent : BaseComposeEvent {
    object OnClickChangeView : CardPaymentScreenEvent
    object OnBack : CardPaymentScreenEvent
    object OnClearSignatureClicked : CardPaymentScreenEvent
    object OnClickVerifySignature: CardPaymentScreenEvent
    data class OnSelectedChipChanged(val index: Int) : CardPaymentScreenEvent
    data class OnDrawSignature(
        val path: MutableList<CardSignatruePathState>,
        val canvasWidth: Int,
        val canvasHeight: Int
    ) : CardPaymentScreenEvent
}