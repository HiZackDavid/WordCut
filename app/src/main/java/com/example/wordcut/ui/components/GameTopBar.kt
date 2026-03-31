package com.example.wordcut.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
    onDictionaryClick: () -> Unit,
    onBack: () -> Unit = {},
    onInfo: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }

                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onInfo) {
                    Icon(Icons.Outlined.Info, contentDescription = "Hint")
                }
            }
            Row {
                LanguageButton(
                    languageCode = selectedDictionaryCode,
                    onClick = onDictionaryClick
                )
            }
            Row {
                Text(
                    text = formatTime(remainingTimeSeconds),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 24.sp
                )
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