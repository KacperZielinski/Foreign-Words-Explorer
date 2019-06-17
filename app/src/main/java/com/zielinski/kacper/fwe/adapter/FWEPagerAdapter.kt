package com.zielinski.kacper.fwe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zielinski.kacper.fwe.database.FWEDatabase
import com.zielinski.kacper.fwe.fragment.NewWordFragment
import com.zielinski.kacper.fwe.fragment.WordListFragment

class FWEPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NewWordFragment()
            1 -> {
                val fragment = WordListFragment()
                fragment.words = FWEDatabase.instance!!.wordDao().getAllWords()
                fragment
            }
            else -> null
        }
    }

    companion object {
        const val NUM_ITEMS = 2
    }
}