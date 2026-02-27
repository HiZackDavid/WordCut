package com.example.wordcut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
    val initialWordUpper = initialWord.uppercase()
    val initialLetters: List<Char?> = initialWordUpper.toList()
    //val words;

    LetterGrid(
        letters = initialLetters,
        nbColumns = initialWord.length+1,
        modifier = Modifier.fillMaxWidth()
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(letters) { letter ->
            LetterCell(letter = letter)
        }

    }
}

@Composable
fun LetterCell(letter: Char?) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                Color(0xFF2FB39A),
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString() ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0B3B33)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordCutGamePreview() {
    WordCutTheme {
        WordCutGame()
    }
}

@Preview(showBackground = true)
@Composable
fun LetterCellPreview() {
        val letter = 'M'
        LetterCell(letter)
}