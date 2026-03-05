package com.example.wordcut.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

private val KEYBOARD_ROWS = listOf(
    "QWERTYUIOP",
    "ASDFGHJKL",
    "⌫ZXCVBNM⏎"
)

private val KeySpacing = 4.dp
private val KeyRowSpacing = 4.dp
private val KeyboardPadding = 8.dp
private val KeyCornerSize = 2.dp

private val KeyDisabledBgColor = Color(0xFFA4AEC4)
private val KeyDisabledTextColor = Color.White

private val KeyEnabledBgColor = Color(0xFFDCE1ED)
private val KeyEnabledTextColor = Color(0xFF5A6376)

private val KeyAvailableBgColor = Color(0xFF79B851)
private val KeyAvailableTextColor = Color.White

private val BadgeBgColor = Color(0xFF4267B2)
private val BadgeTextColor = Color.White
private val BadgeSize = 22.dp

private enum class KeyState { Disabled, Enabled, Available }

@Composable
fun KeyboardLayout(
    availableLetterCounts: Map<Char, Int>,
    onKeyPressed: (Char) -> Unit,
    onDelete: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(KeyboardPadding)
    ) {
        val keySize = ((maxWidth - KeySpacing * 9) / 10f)

        Column(verticalArrangement = Arrangement.spacedBy(KeyRowSpacing)) {
            KEYBOARD_ROWS.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(KeySpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (rowIndex == 1) Spacer(Modifier.width(keySize / 2f))

                    row.forEach { character ->
                        when (character) {
                            '⌫' -> {
                                WideKey(
                                    text = "⌫",
                                    onClick = onDelete,
                                    modifier = Modifier
                                        .height(keySize)
                                        .weight(1.5f)
                                )
                            }
                            '⏎' -> {
                                WideKey(
                                    text = "Enter",
                                    onClick = onSubmit,
                                    modifier = Modifier
                                        .height(keySize)
                                        .weight(1.5f)
                                )
                            }
                            else -> {
                                val count = availableLetterCounts[character] ?: 0
                                val state =
                                    if (count >= 1) KeyState.Available else KeyState.Disabled

                                SquareKey(
                                    letter = character.toString(),
                                    state = state,
                                    modifier = Modifier
                                        .height(keySize)
                                        .weight(1f),
                                    onClick = { onKeyPressed(character) }
                                )
                            }
                        }
                    }

                    if (rowIndex == 1) Spacer(Modifier.width(keySize / 2f))
                }

            }
        }
    }
}

@Composable
private fun SquareKey(
    letter: String,
    state: KeyState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseKey(
        text = letter,
        state = state,
        modifier = modifier.aspectRatio(1f),
        onClick = onClick
    )
}

@Composable
private fun WideKey(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseKey(
        text = text,
        state = KeyState.Enabled,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
private fun BaseKey(
    text: String,
    state: KeyState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp
) {
    val (backgroundColor, textColor, clickable) = when (state) {
        KeyState.Disabled -> Triple(KeyDisabledBgColor, KeyDisabledTextColor, false)
        KeyState.Enabled -> Triple(KeyEnabledBgColor, KeyEnabledTextColor, true)
        KeyState.Available -> Triple(KeyAvailableBgColor, KeyAvailableTextColor, true)
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(KeyCornerSize))
                .background(backgroundColor)
                .clickable(enabled = clickable, onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = fontSize
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
        onDelete = {},
        onSubmit = {}
    )
}