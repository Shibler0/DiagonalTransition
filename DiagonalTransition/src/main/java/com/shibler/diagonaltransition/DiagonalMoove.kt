package com.shibler.diagonaltransition

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.newOffset(moove: Boolean = false, isCurrentContent: Boolean): Modifier = composed {

    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    val offsetX by animateDpAsState(
        targetValue = if (moove) -width else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )
    val offsetY by animateDpAsState(
        targetValue = if (moove) -height else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )
    if(isCurrentContent) {
        this.offset(offsetX, offsetY)
    } else {
        this.offset(offsetX + width, offsetY + height)
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun GraphicTransition(moove: Boolean = false,spaceAround: Dp = 50.dp, shape: Int = 0, color: Color = Color.Blue) {

    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    val offsetX by animateDpAsState(
        targetValue = if(moove) - width  else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )

    val offsetY by animateDpAsState(
        targetValue = if(moove) -height else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )

    Canvas(modifier = Modifier
        .background(color)
        .fillMaxSize()
    ) {
        drawPatterns(offsetX.toPx(), offsetY.toPx(), space = spaceAround, shape)
    }



}

@SuppressLint("SuspiciousIndentation")
fun DrawScope.drawPatterns(offsetX: Float, offsetY: Float, space: Dp, shape: Int) {

    val whiteAlpha = Color(255, 255, 255, 128)

    val shapeSize = 15.dp.toPx()
    val strokeWidth = 2.dp.toPx()

    val spaceBetween = space.toPx()

    for(x in 0 until size.width.toInt() * 2 step spaceBetween.toInt()) {
        for(y in - spaceBetween.toInt() until size.height.toInt() * 2 + spaceBetween.toInt()*2 step  spaceBetween.toInt()) {

            val animateOffsetX = x + offsetX
            val animateOffsetY = y + offsetY

            when(shape) {
                0 -> drawCircle(
                    color = whiteAlpha,
                    center = Offset(animateOffsetX , animateOffsetY ),
                    radius = shapeSize / 2,
                    style = Stroke(width = strokeWidth)
                )
                1 -> drawRect(
                    color = whiteAlpha,
                    topLeft = Offset(animateOffsetX, animateOffsetY),
                    size = Size(shapeSize, shapeSize),
                    style = Stroke(width = strokeWidth)
                )
            }

        }

    }

}