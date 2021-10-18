package com.hfad.douyintest.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hfad.douyintest.home.fragments.HomeMainFragment

class HomeFragmentStateAdapter(fragmentActivity: HomeMainFragment, private val fragments: List<Fragment>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}