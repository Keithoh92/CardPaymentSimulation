package com.example.salestestapp.ui.shimmer.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class CardSignatruePathState(
    val path: Path = Path(),
    val color: Color = Color.Black,
    val stroke: Float = 5f
)
