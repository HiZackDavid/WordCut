package com.example.wordcut.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameTopBar(
    title: String,
    remainingTimeSeconds: Int,
    selectedDictionaryCode: String,
    modifier: Modifier = Modifier,
    onDictionaryClick: () -> Unit,
    onInfoClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DictionarySelectorButton(
                    flagCode = when (selectedDictionaryCode) {
                        "FR" -> "FR"
                        "EN" -> "US"
                        else -> ""
                    },
                    label = selectedDictionaryCode,
                    onClick = onDictionaryClick
                )
                Row {
                    IconButton(onClick = onInfoClick) {
                        Icon(Icons.Outlined.Info, contentDescription = "Hint")
                    }
                }
            }
            Row {
                if (remainingTimeSeconds > 0) {
                    Text(
                        text = formatTime(remainingTimeSeconds),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 24.sp
                    )
                } else {
                    Text(
                        text = "Game Over",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun GameTopBarPreview () {
    GameTopBar(
        title = "WORDCUT",
        remainingTimeSeconds = 101,
        selectedDictionaryCode = "FR",
        onDictionaryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun GameTopBarGameOverPreview () {
    GameTopBar(
        title = "WORDCUT",
        remainingTimeSeconds = 0,
        selectedDictionaryCode = "FR",
        onDictionaryClick = {}
    )
}