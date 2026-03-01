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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisabledScoreCell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(size = 12.dp)
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
    val shape = RoundedCornerShape(size = 12.dp)

    val point1BgColor = Color(0xFF89B32F)
    val point1TextColor = Color.White
    val point2BgColor = Color(0xFF9B2FB3)
    val point2TextColor = Color.White

    Box (
        modifier = modifier
            .clip(shape)
            .background(
                color = Color.White,
                shape = shape
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Row( modifier = Modifier.fillMaxSize() ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(point1BgColor),
                contentAlignment = Alignment.Center
            ) {
                if (point1 > 0) {
                    Text(
                        text = point1.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = point1TextColor
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(point2BgColor),
                contentAlignment = Alignment.Center
            ) {
                if (point2 > 0) {
                    Text(
                        text = point2.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = point2TextColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DisabledScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    DisabledScoreCell(modifier)
}

@Preview
@Composable
fun DualScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    ScoreCell(
        modifier = modifier,
        point1 = 3,
        point2 = 2
    )
}

@Preview
@Composable
fun SingleScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    ScoreCell(
        modifier = modifier,
        point1 = 3
    )
}

@Preview
@Composable
fun NoScoreCellPreview(){
    val modifier = Modifier.aspectRatio(1f)

    ScoreCell(modifier = modifier)
}