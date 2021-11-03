package com.hfad.douyintest.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeFragmentAdapter(fragmentManager: FragmentManager, behavior: Int, private val fragments : List<Fragment>)
    : FragmentPagerAdapter(fragmentManager, behavior) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}
