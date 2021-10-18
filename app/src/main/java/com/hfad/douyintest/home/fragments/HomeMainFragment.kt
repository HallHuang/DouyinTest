package com.hfad.douyintest.home.fragments

import android.graphics.Typeface.*
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.FragmentHomeMainBinding
import com.hfad.douyintest.home.adapter.HomeFragmentAdapter
import com.hfad.douyintest.home.adapter.HomeFragmentStateAdapter

class HomeMainFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private lateinit var fragmentHomeMainBinding: FragmentHomeMainBinding
    private lateinit var vpHomeMain: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var ibSearch: ImageButton

    private val tabTitles = arrayOf("杭州", "关注", "推荐")

    private val fragments by lazy {
        ArrayList<Fragment>().apply {
            this.add(LocalFragment())
            this.add(AttentionFragment())
            this.add(RecommandFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fragmentHomeMainBinding = FragmentHomeMainBinding.inflate(inflater, container, false)
        vpHomeMain = fragmentHomeMainBinding.vpHomeMain
        tabLayout = fragmentHomeMainBinding.tabltHomeMain
        ibSearch = fragmentHomeMainBinding.ibSearch
        return fragmentHomeMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpHomeMain.offscreenPageLimit = fragments.size
        vpHomeMain.adapter = HomeFragmentAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments)
        tabLayout.setupWithViewPager(vpHomeMain)
        for (i in 0 ..2) {
            val customView = View.inflate(tabLayout.context, R.layout.custom_view_textview, null)
            val tab = tabLayout.getTabAt(i)
            val textView = customView.findViewById<TextView>(R.id.tv_custom)
            textView.text = tabTitles[i]
            textView.textSize = 18F
            textView.typeface = DEFAULT
            textView.setTextColor(ContextCompat.getColor(tabLayout.context, R.color.text_gray))
            tab?.customView = customView
        }

        tabLayout.addOnTabSelectedListener(this)


        //Viewpager2+tablayout
//        TabLayoutMediator(tabLayout, vpHomeMain, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
//            val customView = View.inflate(tabLayout.context, R.layout.custom_view_textview, null)
//            val textView = customView.findViewById<TextView>(R.id.tv_custom)
//            textView.text = tabTitles[position]
//            textView.textSize = 17F
//            textView.typeface = DEFAULT
//            textView.setTextColor(resources.getColor(R.color.text_gray))
//            tab.customView = null
//            tab.customView = customView
//        }).attach()

        tabLayout.getTabAt(2)?.select()

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) {
            setTabView(tab, true)
        } else {
            return
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        if (tab != null) {
            setTabView(tab, false)
        } else {
            return
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //
    }

    private fun setTabView(tab : TabLayout.Tab, isSelected : Boolean) {
        val textView = tab.customView?.findViewById<TextView>(R.id.tv_custom)
        if (textView != null) {
            if (isSelected) {
                textView.typeface = DEFAULT_BOLD
                textView.setTextColor(ContextCompat.getColor(tabLayout.context, R.color.white))
            } else {
                textView.typeface = DEFAULT
                textView.setTextColor(ContextCompat.getColor(tabLayout.context, R.color.text_gray))
            }
        }

    }

}
