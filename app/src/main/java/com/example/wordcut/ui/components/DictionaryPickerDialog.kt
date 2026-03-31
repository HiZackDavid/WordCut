package com.example.wordcut.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordcut.domain.models.Dictionary
import com.example.wordcut.domain.models.DictionarySource

@Composable
fun DictionaryPickerDialog (
    dictionaries: List<Dictionary>,
    selectedDictionaryId: String,
    onDismiss: () -> Unit,
    onDictionarySelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select a dictionary") },
        text = {
            Column (verticalArrangement = Arrangement.spacedBy(12.dp)) {
                dictionaries.forEach { dictionary ->
                    val isSelected = dictionary.id == selectedDictionaryId

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isSelected) Color(0xFF79B851) else Color(0xFFDCE1ED),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onDictionarySelected(dictionary.id) }
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = "${dictionary.languageCode} — ${dictionary.displayName}",
                            color = if (isSelected) Color.White else Color(0xFF5A6376)
                        )
                    }
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Preview
@Composable
fun DictionaryPickerDialogPreview () {
    DictionaryPickerDialog(
        dictionaries = listOf(
            Dictionary(
                id = "francais.txt",
                displayName = "French",
                languageCode = "FR",
                source = DictionarySource.Asset("francais.txt")
            ),
        ),
        selectedDictionaryId = "francais.txt",
        onDismiss = {},
        onDictionarySelected = {}
    )
}