package com.zielinski.kacper.fwe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zielinski.kacper.fwe.database.FWEDatabase
import com.zielinski.kacper.fwe.domain.model.Word
import com.zielinski.kacper.fwe.translation.api.YandexAPI
import com.zielinski.kacper.fwe.translation.dto.TranslateResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initializeDatabase()
        registerOnClickListeners()
    }

    private fun registerOnClickListeners() {
        speech_image_view.setOnClickListener {
            getWordFromSpeech()
        }
    }

    private fun initializeDatabase() {
        FWEDatabase.initialize(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getWordFromSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, SPEECH_RESULT_ACTIVITY_CODE)
        } else {
            Toast.makeText(
                this, "Sorry, your device doesn't support speech recognition",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SPEECH_RESULT_ACTIVITY_CODE ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val word = result[0]

                    recognized_word.text = word

                    val restApiCall = YandexAPI.getWordTranslation(word, "en", "pl")
                    restApiCall.enqueue(object : Callback<TranslateResponse> {
                        override fun onResponse(call: Call<TranslateResponse>, response: Response<TranslateResponse>) {
                            val body = response.body()
                            val translatedWord = body?.text!![0]
                            translated_word.text = translatedWord
                            FWEDatabase.instance!!.wordDao().insertWord(Word(word, translatedWord))
                        }

                        override fun onFailure(call: Call<TranslateResponse>, error: Throwable) {
                            // catch error
                        }
                    })
                }
        }
    }

    companion object {
        const val SPEECH_RESULT_ACTIVITY_CODE = 712
    }
}
