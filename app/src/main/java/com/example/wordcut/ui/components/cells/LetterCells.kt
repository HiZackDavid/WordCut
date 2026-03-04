package com.example.wordcut.ui.components.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextFontSize = 20.sp
private val CellBorderSize = 4.dp
private val DisabledBgColor = Color(0xFFa4aec4)
private val CommittedBgColor = Color(0xFF79b851)
private val CommittedTextColor = Color.White
private val WriteCellBorderColor = CommittedBgColor
private val WriteCellTextColor = CommittedBgColor
private val WriteCellEmptyBorderColor = Color(0xFFE5E1E1)
private val WriteCellHighlightedBorderColor = CommittedBgColor
private val WriteCellHighlightBorderWidth = 1.dp

@Composable
fun DisabledCell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = DisabledBgColor,
                shape = RoundedCornerShape(CellBorderSize)
            ),
        contentAlignment = Alignment.Center
    ) {}
}

@Composable
fun CommittedCell(letter: Char, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = CommittedBgColor,
                shape = RoundedCornerShape(CellBorderSize)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            fontSize = TextFontSize,
            fontWeight = FontWeight.Bold,
            color = CommittedTextColor
        )
    }
}

@Composable
fun WriteCell(modifier: Modifier = Modifier, letter: Char? = null, isHighlighted: Boolean = false) {
    val borderColor = when {
        isHighlighted -> WriteCellHighlightedBorderColor
        letter == null -> WriteCellEmptyBorderColor
        else -> WriteCellBorderColor
    }
    Box(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(CellBorderSize)
            )
            .border(
                width = WriteCellHighlightBorderWidth,
                color = borderColor,
                shape = RoundedCornerShape(CellBorderSize)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter?.toString() ?: "",
            fontSize = TextFontSize,
            fontWeight = FontWeight.Bold,
            color = WriteCellTextColor
        )
    }
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun DisabledCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    DisabledCell(modifier)
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun CommittedCellPreview(){
    val letter = 'I'
    val modifier = Modifier.aspectRatio(1f)

    CommittedCell(
        letter = letter,
        modifier = modifier
    )
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun WriteCellPreview() {
    val letter = 'I'
    val modifier = Modifier.aspectRatio(1f)

    WriteCell(
        letter = letter,
        modifier = modifier
    )
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun WriteCellHighlightedPreview() {
    val modifier = Modifier.aspectRatio(1f)

    WriteCell(
        isHighlighted = true,
        modifier = modifier
    )
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun EmptyWriteCellPreview() {
    val modifier = Modifier.aspectRatio(1f)

    WriteCell(
        modifier = modifier
    )
}
