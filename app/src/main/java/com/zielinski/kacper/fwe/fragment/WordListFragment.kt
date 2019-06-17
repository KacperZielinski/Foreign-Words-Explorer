package com.zielinski.kacper.fwe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zielinski.kacper.fwe.R
import com.zielinski.kacper.fwe.adapter.WordListAdapter
import com.zielinski.kacper.fwe.domain.model.Word
import kotlinx.android.synthetic.main.fragment_word_list.*


class WordListFragment : Fragment() {

    lateinit var words: List<Word>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = WordListAdapter(words)
        }
    }
}
