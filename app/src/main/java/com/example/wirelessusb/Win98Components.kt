package com.example.wirelessusb

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Classic Win98 raised border effect
fun Modifier.win98Raised(): Modifier = this.drawBehind {
    // Top & Left — white highlight
    drawLine(Color.White, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = 2f)
    drawLine(Color.White, Offset(0f, 0f), Offset(0f, size.height), strokeWidth = 2f)
    // Bottom & Right — dark shadow
    drawLine(Color(0xFF404040), Offset(0f, size.height), Offset(size.width, size.height), strokeWidth = 2f)
    drawLine(Color(0xFF404040), Offset(size.width, 0f), Offset(size.width, size.height), strokeWidth = 2f)
    // Inner highlight
    drawLine(Color(0xFFDFDFDF), Offset(2f, 2f), Offset(size.width - 2f, 2f), strokeWidth = 1f)
    drawLine(Color(0xFFDFDFDF), Offset(2f, 2f), Offset(2f, size.height - 2f), strokeWidth = 1f)
    // Inner shadow
    drawLine(Color(0xFF808080), Offset(2f, size.height - 2f), Offset(size.width - 2f, size.height - 2f), strokeWidth = 1f)
    drawLine(Color(0xFF808080), Offset(size.width - 2f, 2f), Offset(size.width - 2f, size.height - 2f), strokeWidth = 1f)
}

// Classic Win98 sunken border effect (for input fields, status boxes)
fun Modifier.win98Sunken(): Modifier = this.drawBehind {
    // Top & Left — dark (sunken)
    drawLine(Color(0xFF808080), Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = 2f)
    drawLine(Color(0xFF808080), Offset(0f, 0f), Offset(0f, size.height), strokeWidth = 2f)
    drawLine(Color(0xFF404040), Offset(1f, 1f), Offset(size.width - 1f, 1f), strokeWidth = 1f)
    drawLine(Color(0xFF404040), Offset(1f, 1f), Offset(1f, size.height - 1f), strokeWidth = 1f)
    // Bottom & Right — white (raised)
    drawLine(Color.White, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth = 2f)
    drawLine(Color.White, Offset(size.width, 0f), Offset(size.width, size.height), strokeWidth = 2f)
    drawLine(Color(0xFFDFDFDF), Offset(1f, size.height - 1f), Offset(size.width - 1f, size.height - 1f), strokeWidth = 1f)
    drawLine(Color(0xFFDFDFDF), Offset(size.width - 1f, 1f), Offset(size.width - 1f, size.height - 1f), strokeWidth = 1f)
}

// Win98 style button
@Composable
fun Win98Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isRed: Boolean = false
) {
    var isPressed by remember { mutableStateOf(false) }

    val bgColor = when {
        isRed -> Color(0xFFC0C0C0)
        else -> Color(0xFFC0C0C0)
    }

    Box(
        modifier = modifier
            .background(bgColor)
            .then(if (isPressed) Modifier.win98Sunken() else Modifier.win98Raised())
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                            onClick()
                        }
                    )
                }
            }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = if (isRed) Color(0xFF800000) else Color.Black
        )
    }
}

// Win98 Title Bar
@Composable
fun Win98TitleBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF000080))
            .padding(horizontal = 6.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

    }
}

// Win98 Window container
@Composable
fun Win98Window(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(Color(0xFFC0C0C0))
            .win98Raised()
    ) {
        Win98TitleBar(title)
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

// Win98 Input Field
@Composable
fun Win98TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(2.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Black
            ),
            cursorBrush = SolidColor(Color.Black),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .win98Sunken()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            singleLine = true
        )
    }
}

// Win98 Status indicator
@Composable
fun Win98StatusBar(
    isRunning: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .win98Sunken()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(if (isRunning) Color(0xFF00AA00) else Color(0xFF808080))
                .win98Raised()
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = if (isRunning) "Server Running" else "Server Stopped",
            fontSize = 15.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black
        )
    }
}
