package com.example.salestestapp.ui.shimmer

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.MotionEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.example.salestestapp.ui.shimmer.event.CardPaymentScreenEvent
import com.example.salestestapp.ui.shimmer.model.CardSignatruePathState

@SuppressLint("MutableCollectionMutableState")
@Composable
fun PaintBody(
    path: MutableList<CardSignatruePathState>,
    canvasWidth: Int,
    canvasHeight: Int,
    event: (CardPaymentScreenEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val drawColor = remember{ mutableStateOf(Color.Black)}
        val drawBrush = remember{ mutableStateOf(5f)}
        val usedColor = remember { mutableStateOf(mutableSetOf(Color.Black,Color.White,Color.Gray))}

        path.add(CardSignatruePathState(Path(),drawColor.value,drawBrush.value))

        DrawingCanvas(
            drawColor,
            drawBrush,
            usedColor,
            path,
            canvasWidth,
            canvasHeight,
            event
        )

        val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    }
}

// A composable that listen to all the movements across the XY axis from the current path to all other movements made and draws the line on each movement detected.
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(
    drawColor: MutableState<Color>,
    drawBrush: MutableState<Float>,
    usedColor: MutableState<MutableSet<Color>>,
    path: MutableList<CardSignatruePathState>,
    canvasWidth: Int,
    canvasHeight: Int,
    event: (CardPaymentScreenEvent) -> Unit
) {
    val currentPath = path.last().path
    val movePath = remember{ mutableStateOf<Offset?>(null)}

    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter {
                when(it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        currentPath.moveTo(it.x,it.y)
                        usedColor.value.add(drawColor.value)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        movePath.value = Offset(it.x,it.y)
                    }
                    else -> {
                        movePath.value =null
                    }
                }
                true
            }
    ){
        movePath.value?.let {
            currentPath.lineTo(it.x,it.y)
            drawPath(
                path = currentPath,
                color = drawColor.value,
                style = Stroke(drawBrush.value)
            )
        }
        path.forEach {
            drawPath(
                path = it.path,
                color = it.color,
                style  = Stroke(it.stroke)
            )
        }

        event(CardPaymentScreenEvent.OnDrawSignature(path, canvasWidth, canvasHeight))
    }
}