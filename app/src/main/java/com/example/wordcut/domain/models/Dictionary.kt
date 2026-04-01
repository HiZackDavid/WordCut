package com.example.wordcut.domain.models

data class Dictionary(
    val id: String,
    val displayName: String,
    val languageCode: String,
    val source: DictionarySource
)

sealed interface DictionarySource {
    data class Asset(val fileName: String): DictionarySource
    data class DeviceFile(val filePath: String): DictionarySource
    data class Url(val url: String): DictionarySource
}