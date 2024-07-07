package com.example.salestestapp.ui.compose.components.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.salestestapp.common.ThemePreview
import com.example.salestestapp.ui.theme.halfSpacing
import com.example.salestestapp.ui.theme.spacing24
import com.example.salestestapp.ui.theme.spacing8

@Composable
fun EmptyWithIcon(
    icon: ImageVector,
    message: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    iconSize: Dp = 70.dp
) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(halfSpacing, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Empty Icon",
                modifier = Modifier
                    .size(iconSize)
                    .padding(bottom = spacing8)
            )
            title?.run {
                Text(
                    text = this,
                    modifier = Modifier.padding(horizontal = spacing24),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }
            Text(
                text = message,
                modifier = Modifier.padding(horizontal = spacing24),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@ThemePreview
@Composable
private fun EmptyWithIconPreview(
    @PreviewParameter(EmptyWithIconPreviewProvider::class) titleAndMessage: Pair<String?, String>
) {
    val (title, message) = titleAndMessage
    AppTheme {
        EmptyWithIcon(
            icon = Icons.Default.Whatshot,
            title = title,
            message = message,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private class EmptyWithIconPreviewProvider :
    PreviewParameterProvider<Pair<String?, String>> {
    override val values: Sequence<Pair<String?, String>>
        get() = sequenceOf(
            Pair("No orders", "There are no orders to action"),
            Pair(null, "There are no orders to action")
        )
}