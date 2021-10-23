package com.hfad.douyintest.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CommonFragmentAdapter(fragmentManager: FragmentManager, behavior: Int,
                            private val fragments : List<Fragment>, private val titiles: List<String>)
    : FragmentPagerAdapter(fragmentManager, behavior) {

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence = titiles[position]
}