package com.example.wordcut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
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
import com.example.wordcut.ui.theme.WordCutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                WordCutTheme {
                    WordCutGame(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WordCutGame(modifier: Modifier = Modifier) {
    val initialWord = "Matelas"
    val initialLetters: List<Char> = initialWord.uppercase().toList()
    val nbLetters = initialLetters.size

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        WordCutRow(
            letters = initialLetters,
            nbCells = nbLetters,
            nbActiveCells = nbLetters,
            hasCommited = true,
            scoreIsDisabled = true
        )
        WordCutRow(
            letters = "METAL".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-2,
            hasCommited = true,
            point1 = 2,
            point2 = 2
        )
        WordCutRow(
            letters = "LAME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-3,
            hasCommited = true,
            point1 = 3,
            point2 = 2
        )
        WordCutRow(
            letters = "AME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-4,
            hasCommited = true,
            point1 = 3
        )
        WordCutRow(
            letters = "M".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-5,
            hasCommited = false
        )
    }
}

@Composable
fun WordCutRow(
    letters: List<Char>,
    nbCells: Int,
    nbActiveCells: Int,
    hasCommited: Boolean,
    scoreIsDisabled: Boolean = false,
    point1: Int = 0,
    point2: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val cellModifier = Modifier
            .weight(1f)
            .aspectRatio(1f)

        for (i in 0 until nbCells) {
            when {
                i >= nbActiveCells ->
                    DisabledCell(cellModifier)
                hasCommited && i < letters.size ->
                    CommitedCell(letters[i], cellModifier)
                !hasCommited && i < letters.size ->
                    WriteCell(letter = letters[i], modifier = cellModifier)
                !hasCommited && i < nbActiveCells ->
                    WriteCell(modifier = cellModifier)
                else -> DisabledCell(cellModifier)
            }
        }

        if (scoreIsDisabled) {
            DisabledScoreCell(cellModifier)
        } else {
            ScoreCell(
                cellModifier,
                point1 = point1,
                point2 = point2
            )
        }
    }
}

@Composable
fun DisabledCell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Gray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {}
}

@Composable
fun CommitedCell(letter: Char, modifier: Modifier = Modifier) {
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

@Preview(showBackground = true)
@Composable
fun WordCutGamePreview() {
    WordCutTheme {
        WordCutGame()
    }
}