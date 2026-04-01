package com.example.wordcut.utils

// FlagCodes: https://github.com/acolombo11/flagkit-compose/tree/master/flagkit/src/commonMain/kotlin/flagkit/flags

fun languageToFlagCode(languageCode: String): String {
    return when (languageCode) {
        "FR" -> "FR"
        "EN" -> "US"
        "ES" -> "ES"
        "DE" -> "DE"
        "IT" -> "IT"
        "PT" -> "PT"
        else -> ""
    }
}