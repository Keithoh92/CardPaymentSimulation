package com.example.salestestapp.ui.shimmer.event

import com.example.testui.common.BaseComposeEvent
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.shimmer.model.SelectedOption

sealed interface CardPaymentScreenEvent : BaseComposeEvent {
    data class OnClickDropdownButtonPaymentBreakdown(val currentValue: Boolean) :
        CardPaymentScreenEvent
    data class OnClickDropdownButtonCardSignature(val currentValue: Boolean) :
        CardPaymentScreenEvent
    object OnClickChangeView : CardPaymentScreenEvent
    data class OnSelectedChipChanged(val index: Int) : CardPaymentScreenEvent
    object OnDismissAlertBanner : CardPaymentScreenEvent
    object OnClickBottomSheet : CardPaymentScreenEvent
    data class OnDrawSignature(
        val path: MutableList<CardSignatruePathState>,
        val canvasWidth: Int,
        val canvasHeight: Int
    ) : CardPaymentScreenEvent
    object OnClearSignatureClicked : CardPaymentScreenEvent
    object OnClickVerifySignature: CardPaymentScreenEvent
    data class OnOptionSelected(val selectedOption: SelectedOption): CardPaymentScreenEvent
}