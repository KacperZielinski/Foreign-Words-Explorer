package com.zielinski.kacper.fwe.translation.api

import com.zielinski.kacper.fwe.translation.dto.TranslateResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class YandexAPI {

    companion object {
        private const val MY_MEMORY_API_URL = "https://translate.yandex.net/api/v1.5/tr.json/"
        private const val YANDEX_API_KEY =
            "trnsl.1.1.20190615T184624Z.8b8ed0b6ff4fa904.696d140895079a3ec5f7cb04c04dc5439db35148"

        private val translationAPI: TranslationAPI? = Retrofit.Builder()
            .baseUrl(MY_MEMORY_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranslationAPI::class.java)

        fun getWordTranslation(word: String, fromLanguage: String, toLanguage: String): Call<TranslateResponse> {
            val languageQuery = "$fromLanguage-$toLanguage"
            return translationAPI!!.getWordTranslation(YANDEX_API_KEY, word, languageQuery)
        }
    }
}