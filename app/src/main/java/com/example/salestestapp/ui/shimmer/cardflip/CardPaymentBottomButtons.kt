package com.example.salestestapp.ui.shimmer.cardflip

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.salestestapp.ui.theme.SalesTestAppTheme
import com.example.testui.common.BaseComposeEvent
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.theme.fontSize16
import com.example.salestestapp.ui.theme.pointFive
import com.example.salestestapp.ui.theme.pointSix
import com.example.salestestapp.ui.theme.size20
import com.example.salestestapp.ui.theme.spacing12
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun CardPaymentBottomButtons(
    event: (BaseComposeEvent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = spacing12),
        modifier = Modifier
            .padding(horizontal = spacing8)
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.surface
            ),
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
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.surface
            ),
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

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CardPaymentBottomButtonsPreview() {
    SalesTestAppTheme {
        CardPaymentBottomButtons({})
    }
}