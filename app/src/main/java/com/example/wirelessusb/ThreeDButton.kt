package com.example.wirelessusb

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ThreeDButton(
    text: String,
    baseColor: Color,
    modifier: Modifier = Modifier,
    shadowDepth: Dp = 5.dp,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    val currentShadowDepth by animateDpAsState(
        targetValue = if (isPressed) 0.dp else shadowDepth,
        animationSpec = tween(100),
        label = "shadow"
    )

    val shadowColor = Color(
        red = (baseColor.red * 0.55f),
        green = (baseColor.green * 0.55f),
        blue = (baseColor.blue * 0.55f),
        alpha = 1f
    )

    Box(
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            }
            .drawBehind {
                val shadowOffsetPx = currentShadowDepth.toPx()
                // Shadow layer
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(0f, shadowOffsetPx),
                    size = Size(size.width, size.height),
                    cornerRadius = CornerRadius(0f)
                )
                // Main button face
                drawRoundRect(
                    color = baseColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height - shadowOffsetPx),
                    cornerRadius = CornerRadius(0f)
                )
            }
            .padding(bottom = shadowDepth),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp)
        )
    }
}
