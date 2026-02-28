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

@Composable
fun DisabledCell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Gray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {}
}

@Composable
fun CommittedCell(letter: Char, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF2FB39A),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0B3B33)
        )
    }
}

@Composable
fun WriteCell(modifier: Modifier = Modifier, letter: Char? = null) {
    Box(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(2.dp, Color(0xFF2FB39A), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter?.toString() ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2FB39A)
        )
    }
}

@Preview
@Composable
fun DisabledCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    DisabledCell(modifier)
}

@Preview
@Composable
fun CommittedCellPreview(){
    val letter = 'I'
    val modifier = Modifier.aspectRatio(1f)

    CommittedCell(
        letter = letter,
        modifier = modifier
    )
}

@Preview
@Composable
fun WriteCellPreview() {
    val letter = 'I'
    val modifier = Modifier.aspectRatio(1f)

    WriteCell(
        letter = letter,
        modifier = modifier
    )
}

@Preview
@Composable
fun EmptyWriteCellPreview() {
    val modifier = Modifier.aspectRatio(1f)

    WriteCell(
        modifier = modifier
    )
}