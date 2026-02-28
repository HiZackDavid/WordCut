package com.example.wordcut.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.components.WordCutRow
import com.example.wordcut.ui.components.WordCutRowState
import com.example.wordcut.ui.theme.WordCutTheme

@Composable
fun GameScreen(word: String, modifier: Modifier = Modifier) {
    val letters: List<Char> = word.uppercase().toList()
    val nbLetters = letters.size
    val words = listOf(
        WordCutRowState(
            letters = letters,
            nbCells = nbLetters,
            nbActiveCells = nbLetters,
            hasCommitted = true,
            isScoreDisabled = true
        ),
        WordCutRowState(
            letters = "METAL".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-2,
            hasCommitted = true,
            point1 = 2,
            point2 = 2
        ),
        WordCutRowState(
            letters = "LAME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-3,
            hasCommitted = true,
            point1 = 3,
            point2 = 2
        ),
        WordCutRowState(
            letters = "AME".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-4,
            hasCommitted = true,
            point1 = 3
        ),
        WordCutRowState(
            letters = "M".toList(),
            nbCells = nbLetters,
            nbActiveCells = nbLetters-5,
            hasCommitted = false
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        for (word in words) {
            WordCutRow(word)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameGamePreview() {
    WordCutTheme {
        GameScreen(word = "Matelas")
    }
}