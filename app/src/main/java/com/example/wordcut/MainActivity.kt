package com.example.wordcut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val initialWord = "Metelas"
    val initialLetters: List<Char?> = initialWord.uppercase().toList()

    LetterGrid(
        letters = initialLetters,
        nbColumns = initialWord.length+1,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun LetterGrid(
    letters: List<Char?>,
    nbColumns: Int,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(nbColumns),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(nbColumns) { index ->
            if (index < letters.size) {
                LetterCell(letter = letters[index])
            } else {
                ScoreCell(1, 2)
            }
        }
    }
}

@Composable
fun LetterCell(letter: Char?) {
    val isEmpty = (letter == null)
    val backgroundColor = if (isEmpty) Color(0xFFEFEFEF) else Color(0xFF2FB39A)
    val textColor = if (isEmpty) Color.Transparent else Color(0xFF0B3B33)

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter?.toString() ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun ScoreCell(point1: Int = 0, point2: Int = 0) {
    Box (
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (point1 > 0) {
                Box (
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = point1.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (point1 > 0 && point2 > 0){
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxSize()
                        .background(Color.Black)
                )
            }

            if (point2 > 0) {
                Box (
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
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