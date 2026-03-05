package com.example.wordcut.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val KEYBOARD_ROWS = listOf(
    "QWERTYUIOP",
    "ASDFGHJKL",
    "⌫ZXCVBNM⏎"
)

private val KeyCornerSize = 4.dp
private val KeyDisabledBgColor = Color(0xFFA4AEC4)
private val KeyDisabledTextColor = Color.White
private val KeyEnabledBgColor = Color(0xFFDCE1ED)
private val KeyEnabledTextColor = Color(0xFF5A6376)
private val KeyAvailableBgColor = Color(0xFF79B851)
private val KeyAvailableTextColor = Color.White

private enum class KeyState { Disabled, Enabled, Available }

@Composable
fun KeyboardLayout(
    availableLetterCounts: Map<Char, Int>,
    onKeyPressed: (Char) -> Unit,
    onRestart: () -> Unit,
    onDelete: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KEYBOARD_ROWS.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (rowIndex == 1) Spacer(Modifier.weight(0.5f))

                row.forEach { character ->
                    when (character) {
                        '⌫' -> {
                            KeyboardKey(
                                text = "⌫",
                                state = KeyState.Enabled,
                                onClick = onDelete,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        '⏎' -> {
                            KeyboardKey(
                                text = "Enter",
                                state = KeyState.Enabled,
                                onClick = onSubmit,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        else -> {
                            val count = availableLetterCounts[character] ?: 0
                            val state = when {
                                count >= 1 -> KeyState.Available
                                else -> KeyState.Disabled
                            }

                            KeyboardKey(
                                text = character.toString(),
                                state = state,
                                count = count,
                                modifier = Modifier.weight(1f),
                                onClick = { onKeyPressed(character) }
                            )
                        }
                    }
                }

                if (rowIndex == 1) Spacer(Modifier.weight(0.5f))
            }

        }
    }
}

@Composable
private fun KeyboardKey(
    text: String,
    state: KeyState,
    modifier: Modifier = Modifier,
    count: Int = 0,
    onClick: () -> Unit
) {
    val (backgroundColor, textColor, clickable) = when (state) {
        KeyState.Disabled -> Triple(KeyDisabledBgColor, KeyDisabledTextColor, false)
        KeyState.Enabled -> Triple(KeyEnabledBgColor, KeyEnabledTextColor, true)
        KeyState.Available -> Triple(KeyAvailableBgColor, KeyAvailableTextColor, true)
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(KeyCornerSize))
            .background(backgroundColor)
            .clickable(enabled = clickable, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontSize = 14.sp
        )

        if (text.length == 1 && count >= 2) {
            Text(
                text = "x$count",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = textColor.copy(alpha = 0.95f)
            )
        }
    }
}

@Preview
@Composable
fun KeyboardLayoutPreview(){
    KeyboardLayout(
        availableLetterCounts = emptyMap(),
        onKeyPressed = {},
        onRestart = {},
        onDelete = {},
        onSubmit = {}
    )
}

@Preview(showBackground = true)
@Composable
fun KeyboardLayoutWithTextPreview(){
    val word = "addresser".uppercase()
    val letters = word.toList()
    val counts = letters.groupingBy { it }.eachCount()

    KeyboardLayout(
        availableLetterCounts = counts,
        onKeyPressed = {},
        onRestart = {},
        onDelete = {},
        onSubmit = {}
    )
}