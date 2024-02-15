package com.example.salestestapp.ui.shimmer.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.salestestapp.ui.theme.DarkBlue
import com.example.salestestapp.ui.theme.LightBlue

data class CardPaymentScreenAlertBannerConfig(
    val alertBannerIcon: ImageVector = Icons.Filled.PermDeviceInformation,
    val alertBannerIconTint: Color = DarkBlue,
    val alertBannerBackgroundColor: Color = LightBlue,
    val alertBannerMessage: String = "Please present card",
    val alertBannerSubMessage: String = "",
    val showAlertBanner: Boolean = true,
)
