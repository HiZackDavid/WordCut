package com.example.wordcut.data.datasources

class LocalWordDataSource {
    private val words = listOf(
        "Matelas",
        "Metal",
        "Lame",
        "Addresseur",
        "Addresse",
        "Serre",
        "Banane",
        "Banne",
        "Ballon",
        "Ball",
        "Protester",
        "Protest"
    )

    fun randomWord(): String = words.random()
    fun contains(word: String): Boolean = words.any { it.equals(word, ignoreCase = true) }
}