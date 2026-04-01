package com.example.wordcut.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.wordcut.domain.models.Dictionary
import com.example.wordcut.domain.models.DictionarySource
import com.example.wordcut.ui.theme.WordCutTheme
import com.example.wordcut.utils.languageToFlagCode
import flagkit.Flag

private val PickerHeaderBg = Color(0xFFE7E3D9)
private val PickerSurfaceBg = Color.White
private val PickerTitleColor = Color(0xFF4E4A3F)
private val PickerSubtitleColor = Color(0xFF9AA1B2)

private val DictionaryCardShape = RoundedCornerShape(12.dp)
private val DictionaryCardSelectedBg = Color(0xFF79B851)
private val DictionaryCardDefaultBg = Color(0xFFDCE1ED)
private val DictionaryCardSelectedText = Color.White
private val DictionaryCardDefaultText = Color(0xFF5A6376)

@Composable
fun DictionaryPickerDialog (
    dictionaries: List<Dictionary>,
    selectedDictionaryId: String,
    onDismiss: () -> Unit,
    onDictionarySelected: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = PickerSurfaceBg,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PickerHeaderBg)
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "Dictionary",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PickerTitleColor
                    )

                    Surface(
                        onClick = onDismiss,
                        color = Color.Transparent,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF8C8576),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Select a dictionary",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F3A4C)
                    )
                    Text(
                        text = "⚠️ Selecting will restart the game",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PickerSubtitleColor,
                        textAlign = TextAlign.Center
                    )
                }

                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(dictionaries) { dictionary ->
                        DictionaryPickerItem(
                            dictionary = dictionary,
                            isSelected = dictionary.id == selectedDictionaryId,
                            onClick = { onDictionarySelected(dictionary.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DictionaryPickerItem(
    dictionary: Dictionary,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) DictionaryCardSelectedBg else DictionaryCardDefaultBg
    val textColor = if (isSelected) DictionaryCardSelectedText else DictionaryCardDefaultText

    Surface(
        onClick = onClick,
        shape = DictionaryCardShape,
        color = bgColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Flag(
                code = languageToFlagCode(dictionary.languageCode),
                shape = RoundedCornerShape(3.dp),
                size = DpSize(20.dp, 14.dp)
            )

            Text(
                text = dictionary.displayName,
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DictionaryPickerDialogPreview() {
    WordCutTheme {
        DictionaryPickerDialog(
            dictionaries = listOf(
                Dictionary(
                    id = "english.txt",
                    displayName = "English (US)",
                    languageCode = "EN",
                    source = DictionarySource.Asset("english.txt")
                ),
                Dictionary(
                    id = "spanish.txt",
                    displayName = "Spanish",
                    languageCode = "ES",
                    source = DictionarySource.Asset("spanish.txt")
                ),
                Dictionary(
                    id = "francais.txt",
                    displayName = "French",
                    languageCode = "FR",
                    source = DictionarySource.Asset("francais.txt")
                ),
                Dictionary(
                    id = "german.txt",
                    displayName = "German",
                    languageCode = "DE",
                    source = DictionarySource.Asset("german.txt")
                ),
                Dictionary(
                    id = "portuguese.txt",
                    displayName = "Portuguese",
                    languageCode = "PT",
                    source = DictionarySource.Asset("portuguese.txt")
                ),
                Dictionary(
                    id = "italian.txt",
                    displayName = "Italian",
                    languageCode = "IT",
                    source = DictionarySource.Asset("italian.txt")
                )
            ),
            selectedDictionaryId = "english.txt",
            onDismiss = {},
            onDictionarySelected = {}
        )
    }
}