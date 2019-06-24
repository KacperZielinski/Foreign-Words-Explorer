package com.zielinski.kacper.fwe.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
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

        val speechImageView: ImageView = view.findViewById(R.id.speech_image_view)
        val button: Button = view.findViewById(R.id.new_word_button)
        speechImageView.setOnClickListener(this)
        button.setOnClickListener(this)

        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.speech_image_view -> {
                getWordFromSpeech()
            }
            R.id.new_word_button -> {
                val word = new_word_edit_text.text.toString()
                recognized_word.text = word
                new_word_edit_text.setText("")
                getTranslationFromApi(word)
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
                    getTranslationFromApi(word)
                }
        }
    }

    companion object {
        const val SPEECH_RESULT_ACTIVITY_CODE = 712
    }
}
