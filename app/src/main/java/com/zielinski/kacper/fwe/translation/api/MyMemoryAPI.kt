package com.zielinski.kacper.fwe.translation.api

import com.zielinski.kacper.fwe.translation.dto.WordDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyMemoryAPI : TranslationAPI {

    companion object {
        private const val MY_MEMORY_API_URL = "https://api.mymemory.translated.net/get?q="

        val translationAPI: TranslationAPI? = Retrofit.Builder()
            .baseUrl(MY_MEMORY_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranslationAPI::class.java)
    }

    override fun getWordTranslation(word: String, fromLanguage: String, toLanguage: String): Call<WordDto> {
        val httpWord = word.replace(" ", "%20")
        return translationAPI!!.getWordTranslation(httpWord, fromLanguage, toLanguage)
    }

}