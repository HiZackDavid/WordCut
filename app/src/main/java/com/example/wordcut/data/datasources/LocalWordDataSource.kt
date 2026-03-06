package com.example.wordcut.data.datasources

class LocalWordDataSource {
    private val words = listOf("Matelas", "Addresseur", "Banane", "Ballon", "Protester")

    fun randomWord(): String = words.random()
}