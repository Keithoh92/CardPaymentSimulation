package com.example.salestestapp.ui.shimmer.cardflip

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.pointFive
import com.example.salestestapp.ui.theme.pointSix
import com.example.salestestapp.ui.theme.size20
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing8
import com.example.testui.common.BaseComposeEvent

@Composable
fun CardPaymentBottomButtons(event: (BaseComposeEvent) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = spacing12),
        modifier = Modifier
            .padding(horizontal = spacing8)
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = if (!isSystemInDarkTheme()) {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            } else {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            },
            modifier = Modifier.weight(pointSix)
        ) {
            Icon(
                imageVector = Icons.Outlined.Cancel,
                contentDescription = "Cancel Button",
                Modifier.size(size = size20)
            )
            Text(
                text = "Cancel",
                modifier = Modifier.weight(pointFive),
                textAlign = TextAlign.Center,
                fontSize = fontSize16
            )
        }
        OutlinedButton(
            onClick = { event(CardPaymentScreenEvent.OnClickVerifySignature) },
            colors = if (!isSystemInDarkTheme()) {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            } else {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            },
            modifier = Modifier.weight(pointSix)
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "Accept Button",
                Modifier.size(size = size20)
            )
            Text(
                text = "Accept",
                modifier = Modifier.weight(pointFive),
                textAlign = TextAlign.Center,
                fontSize = fontSize16
            )
        }
    }
}

@ThemePreview
@Composable
fun CardPaymentBottomButtonsPreview() {
    AppTheme {
        CardPaymentBottomButtons {}
    }
}