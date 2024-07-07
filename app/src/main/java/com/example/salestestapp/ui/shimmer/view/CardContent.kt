package com.example.salestestapp.ui.shimmer.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.state.CardPaymentScreenUIState
import com.example.salestestapp.ui.shimmer.view.cardflip.CardPaymentCardFrameFlip
import com.example.salestestapp.ui.shimmer.view.cardflip.CardPaymentCardFrameReading
import com.example.salestestapp.ui.shimmer.view.cardflip.CardPaymentCardFrameSuccess
import com.example.salestestapp.ui.shimmer.view.cardflip.CardPaymentScreenAlertBanner
import com.example.salestestapp.ui.theme.fontSize12
import com.example.salestestapp.ui.theme.full
import com.example.salestestapp.ui.theme.pointFive
import com.example.salestestapp.ui.theme.size16
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing16
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing32
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardFaceContent(
    uiState: CardPaymentScreenUIState,
    event: (CardPaymentScreenEvent) -> Unit,
    contentPadding: PaddingValues
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing8),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            )
            .background(Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(start = spacing12, end = spacing12)) {
            AnimatedVisibility(
                visible = uiState.showAlertBanner,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Row {
                    CardPaymentScreenAlertBanner(
                        bannerText = uiState.cardPaymentAlertBannerConfig.alertBannerMessage,
                        bannerSubText = uiState.cardPaymentAlertBannerConfig.alertBannerSubMessage,
                        showBanner = uiState.cardPaymentAlertBannerConfig.showAlertBanner,
                        icon = uiState.cardPaymentAlertBannerConfig.alertBannerIcon,
                        iconTint = uiState.cardPaymentAlertBannerConfig.alertBannerIconTint,
                        backgroundColor = uiState.cardPaymentAlertBannerConfig.alertBannerBackgroundColor
                    )
                }
            }

            AnimatedVisibility(visible = uiState.cardDetails.isChipAndSignature) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing12)
                        .height(50.dp)
                ) {
                    Text(text = "Chip & Pin - Card requires signature")
                    Row {
                        AnimatedVisibility(visible = uiState.cardSignatureIsShowing) {
                            Text(text = "Please sign below", textAlign = TextAlign.Start)
                        }
                    }
                }
            }

            CardPaymentCardFrameFlip(
                cardFace = uiState.cardFaceLoadingAndDetails,
                getCardAngle = { cardFace -> cardFace.angle },
                isChipAndSignature = uiState.cardDetails.isChipAndSignature,
                front = {
                    CardPaymentCardFrameSuccess(cardDetails = uiState.cardDetails)
                },

                loading = { CardPaymentCardFrameReading() },

                signature = {
                    CardPaymentCardBackFrame(
                        uiState.path,
                        event = event
                    )
                }
            )

            AnimatedVisibility(
                visible = uiState.cardDetails.isChipAndSignature,
                enter = fadeIn(tween(2000)),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spacing12)
                ) {
                    OutlinedButton(
                        onClick = { event(CardPaymentScreenEvent.OnClickChangeView) },
                        colors = if (!isSystemInDarkTheme()) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.surface
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        modifier = Modifier.weight(
                            if (uiState.cardSignatureIsShowing) {
                                pointFive
                            } else {
                                full
                            }
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null,
                            Modifier.size(size = size16)
                        )
                        Spacer(modifier = Modifier.width(spacing8))
                        Text(
                            text = if (uiState.cardSignatureIsShowing) {
                                "Show Card Details"
                            } else {
                                "Show Card Signature"
                            },
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
                        if (uiState.cardSignatureIsShowing) {
                            OutlinedButton(
                                onClick = {
                                    event(CardPaymentScreenEvent.OnClearSignatureClicked)
                                },
                                colors = if (!isSystemInDarkTheme()) {
                                    ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.surface
                                    )
                                } else {
                                    ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
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
                    if (uiState.cardDetails.isChipAndSignature) {
                        spacing24
                    } else {
                        spacing32
                    }
                )
            )
            Column(verticalArrangement = Arrangement.spacedBy(spacing16)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(spacing16),
                        thickness = 2.dp
                    )
                    Text(
                        text = "Payment Breakdown",
                        modifier = Modifier.padding(horizontal = spacing8)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(pointFive),
                        thickness = 2.dp
                    )
                }
                event(
                    CardPaymentScreenEvent.OnSelectedChipChanged(
                        uiState.selectedChipIndex
                    )
                )
                CardPaymentBreakdown()
            }
        }
    }
}