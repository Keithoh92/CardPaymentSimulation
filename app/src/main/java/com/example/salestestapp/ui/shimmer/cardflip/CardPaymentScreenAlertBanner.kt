package com.example.salestestapp.ui.shimmer.cardflip

import android.content.res.Configuration
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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.salestestapp.ui.theme.DarkGreen
import com.example.salestestapp.ui.theme.DarkRed
import com.example.salestestapp.ui.theme.LightGreen
import com.example.salestestapp.ui.theme.LightRed
import com.example.salestestapp.ui.theme.SalesTestAppTheme
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.size12
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing8
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun CardPaymentScreenAlertBanner(
    bannerText: String,
    bannerSubText: String? = null,
    showBanner: Boolean,
    icon: ImageVector,
    iconTint: Color,
    displayDuration: Duration = 5.seconds,
    backgroundColor: Color = MaterialTheme.colors.background,
    onDismissRequest: () -> Unit
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
            backgroundColor = backgroundColor
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
                        textAlign = TextAlign.Start
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
                                fontSize = fontSize16
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CardPaymentScreenAlertBannerPreview(
    @PreviewParameter(CardPaymentScreenAlertBannerPreviewProvider::class) params: CardPaymentScreenAlertBannerPreviewData
) {
    SalesTestAppTheme {
        Surface {
            CardPaymentScreenAlertBanner(
                bannerText = params.bannerText,
                bannerSubText = params.bannerSubText,
                showBanner = true,
                icon = params.icon,
                iconTint = params.iconTint,
                backgroundColor = params.backgroundColor,
                onDismissRequest = {}
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