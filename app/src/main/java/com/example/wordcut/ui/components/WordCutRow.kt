package com.example.wordcut.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.components.cells.CommittedCell
import com.example.wordcut.ui.components.cells.DisabledCell
import com.example.wordcut.ui.components.cells.DisabledScoreCell
import com.example.wordcut.ui.components.cells.ScoreCell
import com.example.wordcut.ui.components.cells.WriteCell

@Composable
fun WordCutRow(
    letters: List<Char>,
    nbCells: Int,
    nbActiveCells: Int,
    hasCommitted: Boolean,
    isScoreDisabled: Boolean = false,
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
                hasCommitted && i < letters.size ->
                    CommittedCell(letters[i], cellModifier)
                !hasCommitted && i < letters.size ->
                    WriteCell(letter = letters[i], modifier = cellModifier)
                !hasCommitted && i < nbActiveCells ->
                    WriteCell(modifier = cellModifier)
                else -> DisabledCell(cellModifier)
            }
        }

        if (isScoreDisabled) {
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

@Preview
@Composable
fun FirstRowPreview() {
    val initialWord = "Matelas"
    val initialLetters: List<Char> = initialWord.uppercase().toList()
    val nbLetters = initialLetters.size

    WordCutRow(
        letters = initialLetters,
        nbCells = nbLetters,
        nbActiveCells = nbLetters,
        hasCommitted = true,
        isScoreDisabled = true
    )
}

@Preview
@Composable
fun DualScorePreview() {
    val initialWord = "Matelas"
    val initialLetters: List<Char> = initialWord.uppercase().toList()
    val nbLetters = initialLetters.size

    WordCutRow(
        letters = "METAL".toList(),
        nbCells = nbLetters,
        nbActiveCells = nbLetters-2,
        hasCommitted = true,
        point1 = 2,
        point2 = 2
    )
}

@Preview
@Composable
fun SingleScorePreview() {
    val initialWord = "Matelas"
    val initialLetters: List<Char> = initialWord.uppercase().toList()
    val nbLetters = initialLetters.size

    WordCutRow(
        letters = "AME".toList(),
        nbCells = nbLetters,
        nbActiveCells = nbLetters-4,
        hasCommitted = true,
        point1 = 3
    )
}

@Preview
@Composable
fun PlayablePreview() {
    val initialWord = "Matelas"
    val initialLetters: List<Char> = initialWord.uppercase().toList()
    val nbLetters = initialLetters.size

    WordCutRow(
        letters = "M".toList(),
        nbCells = nbLetters,
        nbActiveCells = nbLetters-5,
        hasCommitted = false
    )
}