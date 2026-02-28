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

data class WordCutRowState(
    val letters: List<Char>,
    val nbCells: Int,
    val nbActiveCells: Int,
    val hasCommitted: Boolean = false,
    val isScoreDisabled: Boolean = false,
    val point1: Int = 0,
    val point2: Int = 0
)

@Composable
fun WordCutRow(row: WordCutRowState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val cellModifier = Modifier
            .weight(1f)
            .aspectRatio(1f)

        for (i in 0 until row.nbCells) {
            when {
                i >= row.nbActiveCells ->
                    DisabledCell(cellModifier)
                row.hasCommitted && i < row.letters.size ->
                    CommittedCell(row.letters[i], cellModifier)
                !row.hasCommitted && i < row.letters.size ->
                    WriteCell(letter = row.letters[i], modifier = cellModifier)
                !row.hasCommitted && i < row.nbActiveCells ->
                    WriteCell(modifier = cellModifier)
                else -> DisabledCell(cellModifier)
            }
        }

        if (row.isScoreDisabled) {
            DisabledScoreCell(cellModifier)
        } else {
            ScoreCell(
                cellModifier,
                point1 = row.point1,
                point2 = row.point2
            )
        }
    }
}

private fun getDemoData(): Map<String, WordCutRowState>{
    val word = "MATELAS"
    val letters = word.uppercase().toList()

    return mapOf(
        "First Row" to WordCutRowState(
            letters = letters,
            nbCells = letters.size,
            nbActiveCells = letters.size,
            hasCommitted = true,
            isScoreDisabled = true
        ),
        "Dual Score" to WordCutRowState(
            letters = "METAL".toList(),
            nbCells = letters.size,
            nbActiveCells = letters.size-2,
            hasCommitted = true,
            point1 = 2,
            point2 = 2
        ),
        "Single Score" to WordCutRowState(
            letters = "AME".toList(),
            nbCells = letters.size,
            nbActiveCells = letters.size-4,
            hasCommitted = true,
            point1 = 3
        ),
        "Playable" to WordCutRowState(
            letters = "M".toList(),
            nbCells = letters.size,
            nbActiveCells = letters.size-5,
            hasCommitted = false
        )
    )
}

@Preview
@Composable
fun FirstRowPreview() {
    val row = getDemoData()["First Row"]
    if (row != null) WordCutRow(row)
}

@Preview
@Composable
fun DualScorePreview() {
    val row = getDemoData()["Dual Score"]
    if (row != null) WordCutRow(row)
}

@Preview
@Composable
fun SingleScorePreview() {
    val row = getDemoData()["Single Score"]
    if (row != null) WordCutRow(row)
}

@Preview
@Composable
fun PlayablePreview() {
    val row = getDemoData()["Playable"]
    if (row != null) WordCutRow(row)
}