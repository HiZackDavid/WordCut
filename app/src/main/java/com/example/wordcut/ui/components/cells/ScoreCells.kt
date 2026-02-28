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

@Composable
fun DisabledScoreCell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Gray, RoundedCornerShape(12.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.Red,
                start = Offset(0f, size.height),
                end = Offset(size.width, 0f),
                strokeWidth = 6f
            )
        }
    }
}

@Composable
fun ScoreCell(modifier: Modifier = Modifier, point1: Int = 0, point2: Int = 0){
    val shape = RoundedCornerShape(12.dp)

    val point1BgColor = Color(0xFFF2C14E)
    val point1TextColor = Color(0xFF1B1B1B)
    val point2BgColor = Color(0xFF2D6F89)
    val noPointBgColor = Color(0xFFF5F5F5)

    Box (
        modifier = modifier
            .clip(shape)
            .background(
                color = Color.White,
                shape = shape
            )
            .border(
                width = 3.dp,
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
                    .background(
                        if (point1 > 0) point1BgColor else noPointBgColor
                    ),
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
                    .background(Color.Black)
            )

            if (point2 > 0) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(point2BgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = point2.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
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