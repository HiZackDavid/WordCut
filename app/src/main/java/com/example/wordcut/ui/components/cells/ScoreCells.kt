package com.example.wordcut.ui.components.cells

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextFontSize = 18.sp
private val CellBorderSize = 4.dp
private val CellBorderWidth = 1.dp
private val CellBorderColor = Color.Gray
private val DisabledBgColor = Color(0xFFa4aec4)
private val Point1TextColor = Color.White
private val Point1BgColor = Color(0xFF55acee)
private val Point2TextColor = Color.White
private val Point2BgColor = Color(0xFF4267b2)

@Composable
fun DisabledScoreCell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = DisabledBgColor,
                shape = RoundedCornerShape(CellBorderSize)
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.Red,
                start = Offset(x = 0f, y= size.height),
                end = Offset(x = size.width, y = 0f),
                strokeWidth = 6f
            )
        }
    }
}

@Composable
fun ScoreCell(modifier: Modifier = Modifier, point1: Int = 0, point2: Int = 0){
    val shape = RoundedCornerShape(CellBorderSize)

    Box (
        modifier = modifier
            .clip(shape)
            .background(
                color = Color.White,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Row( modifier = Modifier.fillMaxSize() ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Point1BgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = point1.toString(),
                    fontSize = TextFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Point1TextColor
                )
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxSize()
                    .background(CellBorderColor.copy(alpha = 0.2f))
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Point2BgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = point2.toString(),
                    fontSize = TextFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Point2TextColor
                )
            }
        }
    }
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun DisabledScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    DisabledScoreCell(modifier)
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun DualScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    ScoreCell(
        modifier = modifier,
        point1 = 3,
        point2 = 2
    )
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun SingleScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    ScoreCell(
        modifier = modifier,
        point1 = 3
    )
}

@Preview(widthDp = 64, heightDp = 64)
@Composable
fun NoScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    ScoreCell(modifier = modifier)
}