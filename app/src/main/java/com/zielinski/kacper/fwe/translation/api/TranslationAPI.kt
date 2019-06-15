package com.zielinski.kacper.fwe.translation.api

import com.zielinski.kacper.fwe.translation.dto.TranslateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationAPI {

    @GET("translate")
    fun getWordTranslation(
        @Query("key") key: String,
        @Query("text") word: String,
        @Query("lang") language: String
    ): Call<TranslateResponse>

}