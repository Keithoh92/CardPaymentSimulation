package com.example.salestestapp.ui.shimmer.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salestestapp.ui.theme.DarkGreen
import com.example.salestestapp.ui.theme.LightGreen
import com.example.testui.common.BaseComposeEvent
import com.example.salestestapp.ui.shimmer.cardflip.model.CardFace
import com.example.testui.shimmer.effect.CardPaymentEffect
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.model.CardPaymentScreenAlertBannerConfig
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.shimmer.model.SelectedOption
import com.example.salestestapp.ui.shimmer.state.CardPaymentScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _saveBitmap = MutableSharedFlow<Bitmap>()
    val saveBitmap = _saveBitmap.asSharedFlow()

    fun onEvent(event: BaseComposeEvent) {
        when (event) {
            is CardPaymentScreenEvent.OnClickDropdownButtonPaymentBreakdown -> onClickDropdownButtonPaymentBreakdown(
                event.currentValue
            )

            is CardPaymentScreenEvent.OnClickDropdownButtonCardSignature -> onClickDropdownButtonCardSignature(
                event.currentValue
            )

            is CardPaymentScreenEvent.OnClickChangeView -> onClickChangeView()
            is CardPaymentScreenEvent.OnSelectedChipChanged -> onSelectedChipChanged(event.index)
            CardPaymentScreenEvent.OnDismissAlertBanner -> onDismissAlertBanner()
            is CardPaymentScreenEvent.OnClickBottomSheet -> onClickBottomSheet()
            is CardPaymentScreenEvent.OnDrawSignature -> onDrawSignature(event.path, event.canvasWidth, event.canvasHeight)
            is CardPaymentScreenEvent.OnClearSignatureClicked -> onClearSignature()
            is CardPaymentScreenEvent.OnClickVerifySignature -> onClickVerifySignature()
            is CardPaymentScreenEvent.OnOptionSelected -> onOptionSelected(event.selectedOption)
        }
    }

    private fun onOptionSelected(selectedOption: SelectedOption) {
        Log.d("TINTIN", "How many times am I called when I click on the thing")
        val test = cardPaymentUIState.value.applications

        test.forEach {
            it.selected = false
        }

        test.find { it.option == selectedOption.option }?.selected = true

        _cardPaymentUIState.update {
            it.copy(
                applications = test
            )
        }
    }

    private fun showToast(toast: CardPaymentEffect.Toast) = viewModelScope.launch {
        _effect.send(toast)
    }

    private fun isSignatureDrawn() =
        cardPaymentUIState.value.path.any { it.path.isEmpty.not() }

    private fun onClickVerifySignature() = viewModelScope.launch {
        if (!isSignatureDrawn()) {
            showToast(CardPaymentEffect.Toast.SignatureNotDrawnToast)
        }

        val newBitmap = createBitmapFromCanvas(
            cardPaymentUIState.value.canvasWidth,
            cardPaymentUIState.value.canvasHeight,
            cardPaymentUIState.value.path
        )

        if (newBitmap != null) {
            _saveBitmap.emit(newBitmap)
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
        var encryptedImage: String? = null
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

    private fun createTransparentBitmapFromBitmap(bitmap: Bitmap?): Bitmap? {
        if (bitmap != null) {
            val replaceThisColor = Color.White.toArgb()
            val picw = bitmap.width
            val pich = bitmap.height
            val pix = IntArray(picw * pich)
            bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich)
            for (y in 0 until pich) {
                // from left to right
                for (x in 0 until picw) {
                    val index = y * picw + x
                    if (pix[index] == replaceThisColor) {
                        pix[index] = android.graphics.Color.TRANSPARENT
                    }
                }
            }
            bitmap.setPixels(pix, 0, picw, 0, 0, picw, pich)
        }
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

    init {
        simulateCardRead()
    }

    private fun onClickBottomSheet() {
        _cardPaymentUIState.update {
            if (cardPaymentUIState.value.showBottomSheet) {
                it.copy(showBottomSheet = false)
            } else {
                it.copy(showBottomSheet = true)
            }
        }
    }

    private fun onDismissAlertBanner() {
        _cardPaymentUIState.update {
            it.copy(showAlertBanner = false)
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
                cardDetails = cardPaymentUIState.value.cardDetails.copy(isChipAndSignature = true),
                paymentBreakdownIsShowing = false,
                cardFaceLoadingAndDetails = cardPaymentUIState.value.cardFaceLoadingAndDetails.getNext(true),
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

    private fun setIsChipAndPin() {
        _cardPaymentUIState.update {
            it.copy(
                cardFaceLoadingAndDetails = cardPaymentUIState.value.cardFaceLoadingAndDetails.next
            )
        }
    }

    private fun onClickDropdownButtonPaymentBreakdown(currentValue: Boolean) {
        _cardPaymentUIState.update {
            it.copy(
                paymentBreakdownCollapsed = !currentValue
            )
        }
    }

    private fun onClickDropdownButtonCardSignature(currentValue: Boolean) {
        _cardPaymentUIState.update {
            it.copy(cardSignatureCollapsed = !currentValue)
        }
    }
}