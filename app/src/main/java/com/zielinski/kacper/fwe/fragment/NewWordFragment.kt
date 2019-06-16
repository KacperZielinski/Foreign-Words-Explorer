package com.zielinski.kacper.fwe.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.zielinski.kacper.fwe.R
import com.zielinski.kacper.fwe.database.FWEDatabase
import com.zielinski.kacper.fwe.domain.model.Word
import com.zielinski.kacper.fwe.translation.api.YandexAPI
import com.zielinski.kacper.fwe.translation.dto.TranslateResponse
import kotlinx.android.synthetic.main.fragment_new_word.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NewWordFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_word, container, false)

        val button: ImageView = view.findViewById(R.id.speech_image_view)
        button.setOnClickListener(this)

        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.speech_image_view -> {
                getWordFromSpeech()
            }

            else -> {
            }
        }
    }

    private fun getWordFromSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK)

        startActivityForResult(intent, SPEECH_RESULT_ACTIVITY_CODE)
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
