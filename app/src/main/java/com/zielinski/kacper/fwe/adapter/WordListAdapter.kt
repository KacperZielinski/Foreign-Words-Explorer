package com.zielinski.kacper.fwe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zielinski.kacper.fwe.R
import com.zielinski.kacper.fwe.database.FWEDatabase
import com.zielinski.kacper.fwe.domain.model.Word


class WordListAdapter(words: List<Word>) : RecyclerView.Adapter<WordListAdapter.WordListHolder>() {

    private var words: List<Word>? = words

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WordListHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: WordListHolder, position: Int) {
        val word: Word = words!![position]
        holder.bind(word)
    }

    override fun getItemCount(): Int = words!!.size

    inner class WordListHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_word_list_item, parent, false)) {

        private var englishWord: TextView? = null
        private var polishWord: TextView? = null

        init {
            polishWord = itemView.findViewById(R.id.polish_word)
            englishWord = itemView.findViewById(R.id.english_word)

            itemView.setOnLongClickListener{
                val word = words?.get(adapterPosition)
                FWEDatabase.instance!!.wordDao().deleteWord(word!!)
                words = FWEDatabase.instance!!.wordDao().getAllWords()
                notifyDataSetChanged()
                true
            }
        }

        fun bind(word: Word) {
            polishWord?.text = word.wordPL
            englishWord?.text = word.wordEN
        }

    }
}