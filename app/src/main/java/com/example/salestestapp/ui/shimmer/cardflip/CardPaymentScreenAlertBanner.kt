package com.example.salestestapp.ui.shimmer.cardflip

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.DarkGreen
import com.example.salestestapp.ui.theme.DarkRed
import com.example.salestestapp.ui.theme.LightGreen
import com.example.salestestapp.ui.theme.LightRed
import com.example.salestestapp.ui.theme.size12
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentScreenAlertBanner(
    bannerText: String,
    bannerSubText: String? = null,
    showBanner: Boolean,
    icon: ImageVector,
    iconTint: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {

    AnimatedVisibility(
        visible = showBanner,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween()
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween()
        )
    ) {
        Card(
            modifier = Modifier
                .padding(spacing24)
                .fillMaxWidth(),
            shape = RoundedCornerShape(size12),
            colors = CardColors(
                containerColor = backgroundColor,
                contentColor = backgroundColor,
                disabledContainerColor = backgroundColor,
                disabledContentColor = backgroundColor
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing8)
            ) {

                Icon(
                    imageVector = icon,
                    tint = iconTint,
                    contentDescription = null
                )

                Column(verticalArrangement = Arrangement.spacedBy(spacing8), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = bannerText,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    AnimatedVisibility(
                        visible = !bannerSubText.isNullOrEmpty(),
                    ) {
                        bannerSubText?.run {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = bannerSubText,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@ThemePreview
@Composable
private fun CardPaymentScreenAlertBannerPreview(
    @PreviewParameter(CardPaymentScreenAlertBannerPreviewProvider::class) params: CardPaymentScreenAlertBannerPreviewData
) {
    AppTheme {
        Surface {
            CardPaymentScreenAlertBanner(
                bannerText = params.bannerText,
                bannerSubText = params.bannerSubText,
                showBanner = true,
                icon = params.icon,
                iconTint = params.iconTint,
                backgroundColor = params.backgroundColor
            )
        }
    }
}

private class CardPaymentScreenAlertBannerPreviewProvider :
    PreviewParameterProvider<CardPaymentScreenAlertBannerPreviewData> {

    override val values: Sequence<CardPaymentScreenAlertBannerPreviewData> = sequenceOf(
        CardPaymentScreenAlertBannerPreviewData(
            bannerText = "Card read successfully",
            bannerSubText = "Please return card",
            icon = Icons.Filled.CheckCircle,
            iconTint = DarkGreen,
            backgroundColor = LightGreen
        ),
        CardPaymentScreenAlertBannerPreviewData(
            bannerText = "Failed to read card",
            bannerSubText = "",
            icon = Icons.Filled.Cancel,
            iconTint = DarkRed,
            backgroundColor = LightRed
        )
    )
}

private data class CardPaymentScreenAlertBannerPreviewData(
    val bannerText: String,
    val bannerSubText: String,
    val icon: ImageVector,
    val iconTint: Color,
    val backgroundColor: Color
)