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
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object Direction {
    const val DIRECTION_TOPLEFT = 0
    const val DIRECTION_TOPRIGHT = 1
    const val DIRECTION_BOTTOMLEFT = 2
    const val DIRECTION_BOTTOMRIGHT = 3
}

object Shapes {
    const val CIRCLE = 0
    const val SQUARE = 1
    const val POINT = 2
}

fun Modifier.newOffset(moove: Boolean = false, isCurrentContent: Boolean, direction: Int = Direction.DIRECTION_BOTTOMRIGHT): Modifier = composed {

    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    var widthSens: Dp = - width
    var heightSens: Dp = - height

    when(direction) {
        Direction.DIRECTION_BOTTOMRIGHT -> {
            widthSens = - width
        }
        Direction.DIRECTION_BOTTOMLEFT -> {
            widthSens = width
        }
        Direction.DIRECTION_TOPRIGHT -> {
            heightSens = height
        }
        Direction.DIRECTION_TOPLEFT -> {
            heightSens = height
            widthSens = width
        }
    }

    val offsetX by animateDpAsState(
        targetValue = if (moove) widthSens else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )
    val offsetY by animateDpAsState(
        targetValue = if (moove) heightSens else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )


    if(isCurrentContent) {

        this.offset(offsetX, offsetY)

    } else {

        when(direction) {
            Direction.DIRECTION_BOTTOMRIGHT -> {
                this.offset(offsetX + width, offsetY + height)
            }
            Direction.DIRECTION_BOTTOMLEFT -> {
                this.offset(offsetX - width, offsetY + height)
            }
            Direction.DIRECTION_TOPRIGHT -> {
                this.offset(offsetX + width, offsetY - height)
            }
            Direction.DIRECTION_TOPLEFT -> {
                this.offset(offsetX - width, offsetY - height)
            }
            else -> {
                this.offset(offsetX, offsetY)
            }
        }
    }



}


@SuppressLint("SuspiciousIndentation")
@Composable
fun DiagonalTransition(moove: Boolean = false,spaceAround: Dp = 50.dp, shape: Int = 0, color: Color = Color.Blue, direction: Int = Direction.DIRECTION_BOTTOMRIGHT) {

    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    var widthSens: Dp = width
    var heightSens: Dp = - height


    when(direction) {
        Direction.DIRECTION_BOTTOMRIGHT -> {
            widthSens = - width
        }

        Direction.DIRECTION_BOTTOMLEFT -> {
            widthSens = width
        }

        Direction.DIRECTION_TOPRIGHT -> {
            heightSens =  height
            widthSens = - width
        }

        Direction.DIRECTION_TOPLEFT -> {
            heightSens = height
        }
    }

    val offsetX by animateDpAsState(
        targetValue = if(moove) widthSens  else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )

    val offsetY by animateDpAsState(
        targetValue = if(moove) heightSens else 0.dp,
        animationSpec = tween(durationMillis = 1800),
        label = ""
    )

    Canvas(modifier = Modifier
        .background(color)
        .fillMaxSize()
    ) {
        drawPattern(offsetX.toPx(), offsetY.toPx(), space = spaceAround, shape)
    }



}

@SuppressLint("SuspiciousIndentation")
fun DrawScope.drawPattern(offsetX: Float, offsetY: Float, space: Dp, shape: Int = Shapes.CIRCLE) {

    val whiteAlpha = Color(255, 255, 255, 128)

    val shapeSize = 15.dp.toPx()
    val strokeWidth = 2.dp.toPx()

    val spaceBetween = space.toPx()

    for(x in 0 until size.width.toInt() * 2 step spaceBetween.toInt()) {
        for(y in - spaceBetween.toInt() until size.height.toInt() * 2 + spaceBetween.toInt() * 2 step  spaceBetween.toInt()) {

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
                2 -> drawPoints(
                    points = listOf(Offset(animateOffsetX, animateOffsetY)),
                    pointMode = PointMode.Points,
                    color = whiteAlpha,
                    strokeWidth = strokeWidth
                )
            }

        }

    }

}