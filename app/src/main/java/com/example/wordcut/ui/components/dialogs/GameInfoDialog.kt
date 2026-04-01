package com.example.wordcut.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.ui.theme.WordCutTheme

private val InfoSectionTitle = Color(0xFF4E4A3F)
private val InfoBodyText = Color(0xFF8C8576)

@Composable
fun GameInfoDialog(
    onDismiss: () -> Unit
) {
    DialogBase(
        title = "Game Info",
        onDismiss = onDismiss
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Goal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = InfoSectionTitle
            )

            Text(
                text = "Create a new valid word from the current word by removing 1, 2 or 3 letters.",
                style = MaterialTheme.typography.bodyLarge,
                color = InfoBodyText
            )

            Text(
                text = "Rules",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = InfoSectionTitle
            )

            Text(
                text = "• The new word must exist in the selected dictionary.\n" +
                        "• You may only use letters from the current word.\n" +
                        "• You must remove between 1 and 3 letters.\n" +
                        "• The game ends when you reach a word of 3 letters or fewer.",
                style = MaterialTheme.typography.bodyLarge,
                color = InfoBodyText
            )

            Text(
                text = "Scoring",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = InfoSectionTitle
            )

            Text(
                text = "• Remove 1 letter: 3 points\n" +
                        "• Remove 2 letters: 2 points\n" +
                        "• Remove 3 letters: 1 point\n" +
                        "• If the order of the remaining letters changes: +2 points",
                style = MaterialTheme.typography.bodyLarge,
                color = InfoBodyText
            )
        }
    }
}

@Preview
@Composable
fun GameInfoDialogPreview() {
    WordCutTheme {
        GameInfoDialog(onDismiss = {})
    }
}