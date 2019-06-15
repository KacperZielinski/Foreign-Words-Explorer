package com.zielinski.kacper.fwe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zielinski.kacper.fwe.database.FWEDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
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
        fab.setOnClickListener {
            getWordFromSpeech()
        }
    }

    private fun initializeDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            FWEDatabase::class.java, FWE_DB_NAME
        ).build()
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
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

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
                    recognized_word.text = result[0]
                }
        }
    }

    companion object {
        const val FWE_DB_NAME = "FWE"
        const val SPEECH_RESULT_ACTIVITY_CODE = 712
        lateinit var db: RoomDatabase
    }
}
