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
import com.example.salestestapp.ui.compose.contract.ComposeContract
import com.example.salestestapp.ui.compose.contract.LoadingType
import com.example.salestestapp.ui.compose.contract.composeContractDelegate
import com.example.salestestapp.ui.shimmer.effect.CardPaymentEffect
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.model.CardPaymentScreenAlertBannerConfig
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState
import com.example.salestestapp.ui.shimmer.model.SelectedOption
import com.example.salestestapp.ui.shimmer.state.CardPaymentScreenUIState
import com.example.salestestapp.ui.shimmer.view.cardflip.model.CardFace
import com.example.salestestapp.ui.theme.DarkGreen
import com.example.salestestapp.ui.theme.LightGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class CardPaymentScreenViewModel@Inject constructor()
    : ViewModel(),
    ComposeContract<CardPaymentScreenUIState, CardPaymentScreenEvent, CardPaymentEffect>
    by composeContractDelegate(CardPaymentScreenUIState.initial())
{

    var isSignature = false

    init {
        setLoadingResult(LoadingType.WithTitle())
        simulateCardRead()
    }

    override fun onEvent(event: CardPaymentScreenEvent) {
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
        emitEffect(CardPaymentEffect.Navigation.Back)
    }

    private fun showToast(toast: CardPaymentEffect.Toast) = viewModelScope.launch {
        emitEffect(toast)
    }

    private fun isSignatureDrawn() = uiState.value.path.any { it.path.isEmpty.not() }

    private fun onClickVerifySignature() = viewModelScope.launch {
        if (!isSignature) return@launch
        if (!isSignatureDrawn()) { showToast(CardPaymentEffect.Toast.SignatureNotDrawnToast) }

        val newBitmap = createBitmapFromCanvas(
            uiState.value.canvasWidth,
            uiState.value.canvasHeight,
            uiState.value.path
        )

        if (newBitmap != null) {
            emitEffect(CardPaymentEffect.OnAcceptSignature(newBitmap))
        }
    }

    private fun onClearSignature() {
        uiState.value.path.clear()
        setLoadedResult {
            copy(
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
        setLoadedResult {
            copy(
                path = path,
                canvasWidth = canvasWidth,
                canvasHeight = canvasHeight
            )
        }
    }

    private fun onSelectedChipChanged(index: Int) {
        setLoadedResult {
            copy(
                selectedChipIndex = index,
                paymentBreakdownIsShowing = index == 0
            )
        }
    }

    private fun onClickChangeView() {
        val cardSignatureViewIsShowing = uiState.value.cardSignatureIsShowing

        setLoadedResult {
            copy(
                cardSignatureIsShowing = !cardSignatureViewIsShowing,
                cardFaceLoadingAndDetails = uiState.value.cardFaceLoadingAndDetails
                    .getNext(true),
            )
        }
    }

    private fun simulateCardRead() = viewModelScope.launch {
        setLoadedResult { CardPaymentScreenUIState.initial() }
        delay(6000)
        val testing: ArrayList<String> = arrayListOf("Visa", "Mastercard")

        val apps = testing.map {
            SelectedOption(it, false)
        }

        setLoadedResult {
            copy(
                cardReadSuccessfully = true,
                cardDetails = uiState.value.cardDetails.copy(isChipAndSignature = isSignature),
                paymentBreakdownIsShowing = false,
                cardFaceLoadingAndDetails = uiState.value.cardFaceLoadingAndDetails.getNext(isSignature),
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