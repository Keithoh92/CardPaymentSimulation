package com.example.salestestapp.ui.shimmer.cardflip

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salestestapp.R
import com.example.salestestapp.ui.compose.TopAppBar
import com.example.salestestapp.ui.shimmer.CardPaymentBreakdown
import com.example.salestestapp.ui.shimmer.CardPaymentCardBackFrame
import com.example.salestestapp.ui.shimmer.model.CardDetails
import com.example.salestestapp.ui.shimmer.state.CardPaymentScreenUIState
import com.example.salestestapp.ui.theme.SalesTestAppTheme
import com.example.salestestapp.ui.theme.fontSize12
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.pointFive
import com.example.salestestapp.ui.theme.size16
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing32
import com.example.salestestapp.ui.theme.spacing8
import com.example.testui.common.BaseComposeEvent
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.model.PaymentBreakdown
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CardFaceDisplay(
    cardPaymentUIState: StateFlow<CardPaymentScreenUIState>,
    event: (BaseComposeEvent) -> Unit
) {

    val uiState = cardPaymentUIState.collectAsState()

    SalesTestAppTheme {
        Scaffold(
            topBar = { TopAppBar(onBack = { event(CardPaymentScreenEvent.OnBack) }) },
            bottomBar = { CardPaymentBottomButtons(event) },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacing8),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = it.calculateBottomPadding()
                        )
                        .background(Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = spacing12,
                            end = spacing12
                        )
                    ) {

                        AnimatedVisibility(
                            visible = uiState.value.showAlertBanner,
                            enter = fadeIn(animationSpec = tween(1000)),
                            exit = fadeOut(animationSpec = tween(500))
                        ) {
                            Row {
                                CardPaymentScreenAlertBanner(
                                    bannerText = uiState.value.cardPaymentAlertBannerConfig.alertBannerMessage,
                                    bannerSubText = uiState.value.cardPaymentAlertBannerConfig.alertBannerSubMessage,
                                    showBanner = uiState.value.cardPaymentAlertBannerConfig.showAlertBanner,
                                    icon = uiState.value.cardPaymentAlertBannerConfig.alertBannerIcon,
                                    iconTint = uiState.value.cardPaymentAlertBannerConfig.alertBannerIconTint,
                                    backgroundColor = uiState.value.cardPaymentAlertBannerConfig.alertBannerBackgroundColor
                                )
                            }
                        }

                        AnimatedVisibility(visible = uiState.value.cardDetails.isChipAndSignature) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(spacing12)
                                    .height(50.dp)
                            ) {
                                Text(
                                    text = "Chip & Pin - Card requires signature",
                                )
                                Row {
                                    AnimatedVisibility(visible = uiState.value.cardSignatureIsShowing) {
                                        Text(
                                            text = "Please sign below",
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                        }

                        CardPaymentCardFrameFlip(
                            cardFace = uiState.value.cardFaceLoadingAndDetails,
                            getCardAngle = { cardFace -> cardFace.angle },
                            isChipAndSignature = uiState.value.cardDetails.isChipAndSignature,
                            front = {
                                CardPaymentCardFrameSuccess(cardDetails = uiState.value.cardDetails)
                            },

                            loading = {
                                CardPaymentCardFrameReading(cardDetails = uiState.value.cardDetails)
                            },

                            signature = {
                                CardPaymentCardBackFrame(
                                    uiState.value.path,
                                    event = event
                                )
                            }
                        )

                        AnimatedVisibility(
                            visible = uiState.value.cardDetails.isChipAndSignature,
                            enter = fadeIn(tween(1000))
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = spacing12)
                            ) {
                                OutlinedButton(
                                    onClick = { event(CardPaymentScreenEvent.OnClickChangeView) },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colors.primary,
                                        backgroundColor = MaterialTheme.colors.surface
                                    ),
                                    modifier = Modifier.weight(if (uiState.value.cardSignatureIsShowing) pointFive else full)

                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Refresh,
                                        contentDescription = null,
                                        Modifier.size(size = size16)
                                    )
                                    Spacer(modifier = Modifier.width(spacing8))
                                    Text(
                                        text = if (uiState.value.cardSignatureIsShowing) "Show Card Details" else "Show Card Signature",
                                        fontSize = fontSize12,
                                        maxLines = 1
                                    )
                                }

                                Spacer(modifier = Modifier.width(spacing8))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(pointFive)
                                ) {
                                    AnimatedVisibility(
                                        visible = uiState.value.cardSignatureIsShowing,
                                        exit = fadeOut(animationSpec = tween(100)),
                                    ) {
                                        OutlinedButton(
                                            onClick = { event(CardPaymentScreenEvent.OnClearSignatureClicked) },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = MaterialTheme.colors.primary,
                                                backgroundColor = MaterialTheme.colors.surface
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Draw,
                                                contentDescription = "Clear Signature",
                                                Modifier.size(size = size16)
                                            )
                                            Spacer(modifier = Modifier.width(spacing8))

                                            Text(
                                                text = "Clear Signature",
                                                textAlign = TextAlign.Center,
                                                fontSize = fontSize12
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(
                                if (uiState.value.cardDetails.isChipAndSignature) {
                                    spacing24
                                } else {
                                    spacing32
                                }
                            )
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(spacing16)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Divider(
                                    thickness = 2.dp,
                                    modifier = Modifier
                                        .height(2.dp)
                                        .width(spacing16)
                                )
                                Text(
                                    text = "Payment Breakdown",
                                    modifier = Modifier.padding(horizontal = spacing8)
                                )
                                Divider(
                                    thickness = 2.dp,
                                    modifier = Modifier.weight(pointFive)
                                )
                            }

                            event(
                                CardPaymentScreenEvent.OnSelectedChipChanged(
                                    uiState.value.selectedChipIndex
                                )
                            )
                            CardPaymentBreakdown()
                        }
                    }
                }
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun CardFaceDisplayPreview() {
    val cardPaymentScreenUIState = MutableStateFlow(
        CardPaymentScreenUIState(
            cardDetails = CardDetails(
                cardHolder = "Keith O'Hare",
                cardNumber = "1840932124567854",
                cardType = "DINERS",
                cardTypeSymbolResource = R.drawable.diners_logo,
                expiryDate = "08/25",
                isChipAndSignature = true
            ),
            paymentBreakdown = PaymentBreakdown(
                amount = "20,00",
                surcharge = "0,00",
                total = "20,00"
            ),
            acceptAndPrintButtonEnabled = true,
            acceptButtonEnabled = true,
            verifySignatureButtonEnabled = false,
            cardReadSuccessfully = true,
            paymentBreakdownIsShowing = true,
            selectedChipIndex = 0,
            cardSignatureIsShowing = false,
        )
    )

    SalesTestAppTheme {
        CardFaceDisplay(
            cardPaymentUIState = cardPaymentScreenUIState,
            event = {}
        )
    }
}