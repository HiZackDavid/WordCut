package com.example.wordcut.data.repositories

import com.example.wordcut.data.datasources.AssetDictionaryDataSource
import com.example.wordcut.domain.repositories.WordRepository
import javax.inject.Inject

class LocalWordRepository @Inject constructor(
    private val assetDataSource: AssetDictionaryDataSource
): WordRepository {
    override suspend fun getRandomWord(dictionaryId: String): String {
        return assetDataSource.randomWord(fileName = dictionaryId)
    }
    override suspend fun isValidWord(dictionaryId: String, word: String): Boolean {
        return assetDataSource.contains(fileName = dictionaryId, word = word)
    }
}