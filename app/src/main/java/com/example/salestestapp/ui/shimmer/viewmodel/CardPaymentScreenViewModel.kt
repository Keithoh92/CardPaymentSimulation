package com.example.salestestapp.ui.shimmer.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.ui.shimmer.cardflip.model.CardFace
import com.example.salestestapp.ui.shimmer.effect.CardPaymentEffect
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.model.CardPaymentScreenAlertBannerConfig
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.shimmer.model.SelectedOption
import com.example.salestestapp.ui.shimmer.state.CardPaymentScreenUIState
import com.example.salestestapp.ui.theme.DarkGreen
import com.example.salestestapp.ui.theme.LightGreen
import com.example.testui.common.BaseComposeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class CardPaymentScreenViewModel@Inject constructor() : ViewModel() {

    private var _cardPaymentUIState = MutableStateFlow(CardPaymentScreenUIState())
    val cardPaymentUIState = _cardPaymentUIState.asStateFlow()

    private val _effect = Channel<CardPaymentEffect>(Channel.UNLIMITED)
    val effect: Flow<CardPaymentEffect> = _effect.receiveAsFlow()

    var isSignature = false

    init {
        simulateCardRead()
    }

    fun onEvent(event: BaseComposeEvent) {
        when (event) {
            is CardPaymentScreenEvent.OnClickChangeView -> onClickChangeView()
            is CardPaymentScreenEvent.OnSelectedChipChanged -> onSelectedChipChanged(event.index)
            is CardPaymentScreenEvent.OnDrawSignature -> onDrawSignature(event.path, event.canvasWidth, event.canvasHeight)
            is CardPaymentScreenEvent.OnClearSignatureClicked -> onClearSignature()
            is CardPaymentScreenEvent.OnClickVerifySignature -> onClickVerifySignature()
            is CardPaymentScreenEvent.OnBack -> navigateBack()
        }
    }

    private fun navigateBack() = viewModelScope.launch {
        _effect.send(CardPaymentEffect.Navigation.Back)
    }

    private fun showToast(toast: CardPaymentEffect.Toast) = viewModelScope.launch {
        _effect.send(toast)
    }

    private fun isSignatureDrawn() = cardPaymentUIState.value.path.any { it.path.isEmpty.not() }

    private fun onClickVerifySignature() = viewModelScope.launch {
        if (!isSignature) return@launch
        if (!isSignatureDrawn()) { showToast(CardPaymentEffect.Toast.SignatureNotDrawnToast) }

        val newBitmap = createBitmapFromCanvas(
            cardPaymentUIState.value.canvasWidth,
            cardPaymentUIState.value.canvasHeight,
            cardPaymentUIState.value.path
        )

        if (newBitmap != null) {
            _effect.send(CardPaymentEffect.OnAcceptSignature(newBitmap))
        }
    }

    private fun onClearSignature() {
        cardPaymentUIState.value.path.clear()
        _cardPaymentUIState.update {
            it.copy(
                path = mutableListOf(
                    CardSignatruePathState(path = Path())
                )
            )
        }
    }

    private fun createBitmapFromCanvas(canvasWidth: Int, canvasHeight: Int, path: List<CardSignatruePathState>): Bitmap? {
        var bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        // Draw the canvas content onto the bitmap
        canvas.drawColor(Color.Transparent.toArgb(), PorterDuff.Mode.CLEAR)
        // Draw the paths onto the bitmap
        path.forEach {
            canvas.drawPath(it.path.asAndroidPath(), Paint().apply {
                color = it.color.toArgb()
                style = Paint.Style.STROKE
                strokeWidth = it.stroke
            })
        }

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos)
        return bitmap
    }

    private fun onDrawSignature(
        path: MutableList<CardSignatruePathState>,
        canvasWidth: Int,
        canvasHeight: Int
    ) {
        _cardPaymentUIState.update {
            it.copy(
                path = path,
                canvasWidth = canvasWidth,
                canvasHeight = canvasHeight
            )
        }
    }

    private fun onSelectedChipChanged(index: Int) {
        _cardPaymentUIState.update {
            it.copy(
                selectedChipIndex = index,
                paymentBreakdownIsShowing = index == 0
            )
        }
    }

    private fun onClickChangeView() {
        val cardSignatureViewIsShowing = cardPaymentUIState.value.cardSignatureIsShowing

        _cardPaymentUIState.update {
            it.copy(
                cardSignatureIsShowing = !cardSignatureViewIsShowing,
                cardFaceLoadingAndDetails = cardPaymentUIState.value.cardFaceLoadingAndDetails.getNext(true),
            )
        }
    }

    private fun simulateCardRead() = viewModelScope.launch {
        delay(6000)
        val testing: ArrayList<String> = arrayListOf("Visa", "Mastercard")

        val apps = testing.map {
            SelectedOption(it, false)
        }

        _cardPaymentUIState.update {
            it.copy(
                cardReadSuccessfully = true,
                cardDetails = cardPaymentUIState.value.cardDetails.copy(isChipAndSignature = isSignature),
                paymentBreakdownIsShowing = false,
                cardFaceLoadingAndDetails = cardPaymentUIState.value.cardFaceLoadingAndDetails.getNext(isSignature),
                currentCardFace = CardFace.Loading,
                cardSignatureIsShowing = true,
                selectedChipIndex = 1,
                showAlertBanner = true,
                showSelectionDialog = false,
                applications = apps,
                cardPaymentAlertBannerConfig = CardPaymentScreenAlertBannerConfig(
                    alertBannerBackgroundColor = LightGreen,
                    alertBannerIconTint = DarkGreen,
                    alertBannerIcon = Icons.Filled.CheckCircle,
                    alertBannerMessage = "Card read successfully",
                    alertBannerSubMessage = "Please return card",
                    showAlertBanner = true
                )
            )
        }
    }
}