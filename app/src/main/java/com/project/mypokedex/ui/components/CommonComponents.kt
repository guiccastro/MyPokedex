package com.project.mypokedex.ui.components

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.RotationalImageSide
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.HomeScreenBackground

@Composable
fun Background() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeScreenBackground)
    )
}

fun Modifier.innerShadow(
    color: Color = Black,
    cornersRadius: Dp = 0.dp,
    spread: Dp = 0.dp,
    blur: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawWithContent {

    drawContent()

    val rect = Rect(Offset.Zero, size)
    val paint = Paint()

    drawIntoCanvas {

        paint.color = color
        paint.isAntiAlias = true
        it.saveLayer(rect, paint)
        it.drawRoundRect(
            left = rect.left,
            top = rect.top,
            right = rect.right,
            bottom = rect.bottom,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
        val left = if (offsetX > 0.dp) {
            rect.left + offsetX.toPx()
        } else {
            rect.left
        }
        val top = if (offsetY > 0.dp) {
            rect.top + offsetY.toPx()
        } else {
            rect.top
        }
        val right = if (offsetX < 0.dp) {
            rect.right + offsetX.toPx()
        } else {
            rect.right
        }
        val bottom = if (offsetY < 0.dp) {
            rect.bottom + offsetY.toPx()
        } else {
            rect.bottom
        }
        paint.color = Black
        it.drawRoundRect(
            left = left + spread.toPx() / 2,
            top = top + spread.toPx() / 2,
            right = right - spread.toPx() / 2,
            bottom = bottom - spread.toPx() / 2,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
        frameworkPaint.xfermode = null
        frameworkPaint.maskFilter = null
    }
}

fun Modifier.customShadow(
    color: Color = Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    spread: Dp = 0f.dp,
    widthOffset: Dp = 0.dp,
    heightOffset: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
) = this.drawBehind {
    this.drawIntoCanvas {
        size.height
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPixel = spread.toPx()
        val leftPixel = (0f - spreadPixel) + offsetX.toPx() - widthOffset.toPx()
        val topPixel = (0f - spreadPixel) + offsetY.toPx() - heightOffset.toPx()
        val rightPixel = (this.size.width + spreadPixel) + widthOffset.toPx()
        val bottomPixel = (this.size.height + spreadPixel) + heightOffset.toPx()

        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter =
                (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        it.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint
        )
    }
}


fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawWithContent {
            drawContent()
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.topBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawWithContent {
            drawContent()
            val width = size.width
            val height = strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

@Composable
fun RotationalImage(frontImage: String, backImage: String, modifier: Modifier) {
    val rotationSensitivity = 0.5F
    var degrees by remember { mutableFloatStateOf(0F) }
    var side by remember { mutableStateOf(RotationalImageSide.Front) }
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        degrees += dragAmount.x * rotationSensitivity
                        if (degrees < -275F) {
                            degrees = 90F
                        }
                        if (degrees > 275F) {
                            degrees = -90F
                        }
                        side =
                            if (degrees < 90F && degrees > -90) RotationalImageSide.Front else RotationalImageSide.Back
                        change.consume()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (side == RotationalImageSide.Front && degrees == 0F) {
                            degrees = 180F
                            side = RotationalImageSide.Back
                        } else {
                            degrees = 0F
                            side = RotationalImageSide.Front
                        }
                    }
                )
            }
            .graphicsLayer {
                rotationY = degrees
            }
    ) {
        SubcomposeAsyncImage(
            model = frontImage,
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .alpha(if (side == RotationalImageSide.Front) 1F else 0F),
            imageLoader = LocalContext.current.imageLoader,
            filterQuality = FilterQuality.High
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(40.dp),
                        color = BorderBlack
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier
                            .padding(40.dp),
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(BorderBlack)
                    )
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        SubcomposeAsyncImage(
            model = backImage,
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = 180F
                }
                .alpha(if (side == RotationalImageSide.Back) 1F else 0F),
            imageLoader = LocalContext.current.imageLoader,
            filterQuality = FilterQuality.High
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(40.dp),
                        color = BorderBlack
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier
                            .padding(40.dp),
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(BorderBlack)
                    )
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}