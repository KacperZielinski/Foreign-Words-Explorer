package com.zielinski.kacper.fwe.translation.api

import com.zielinski.kacper.fwe.translation.dto.WordDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TranslationAPI {

    @GET("/{word}&langpair={fromLanguage}|{toLanguage}")
    fun getWordTranslation(
        @Path("word") word: String,
        @Path("fromLanguage") fromLanguage: String,
        @Path("toLanguage") toLanguage: String
    ): Call<WordDto>

}