package com.zielinski.kacper.fwe.translation.dto

data class TranslateResponse(
    var code: Int,
    var lang: String,
    var text: List<String>
)