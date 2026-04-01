package com.example.wordcut.data.datasources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetDictionaryDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val cache = mutableMapOf<String, Set<String>>()

    fun loadWords(fileName: String): Set<String> {
        return cache.getOrPut(fileName) {
            context.assets.open("dictionaries/$fileName")
                .bufferedReader()
                .useLines { lines ->
                    lines.map { it.trim().uppercase() }
                        .filter { it.isNotBlank() }
                        .toSet()
                }
        }
    }

    fun randomWord(fileName: String, minLength: Int = 5): String {
        val candidates = loadWords(fileName).filter { it.length >= minLength }
        return candidates.random()
    }

    fun contains(fileName: String, word: String): Boolean {
        return loadWords(fileName).contains(word.trim().uppercase())
    }
}