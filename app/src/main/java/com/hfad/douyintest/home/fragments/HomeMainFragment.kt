package com.hfad.douyintest.home.fragments

import android.graphics.Typeface.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.FragmentHomeMainBinding
import com.hfad.douyintest.home.adapter.HomeFragmentStateAdapter

/**
 * 包含三个Fragment的ViewPager2+TabLayout组成顶部滑动导航布局,设置customView自定义tabItem的视图默认/切换后格式
 */
class HomeMainFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private lateinit var fragmentHomeMainBinding: FragmentHomeMainBinding
    private lateinit var vpHomeMain: ViewPager2
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
    ): View {
        fragmentHomeMainBinding = FragmentHomeMainBinding.inflate(inflater, container, false)
        vpHomeMain = fragmentHomeMainBinding.vpHomeMain
        tabLayout = fragmentHomeMainBinding.tabltHomeMain
        ibSearch = fragmentHomeMainBinding.ibSearch
        return fragmentHomeMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpHomeMain.offscreenPageLimit = fragments.size
        val homeFragmentStateAdapter = HomeFragmentStateAdapter(this, fragments)
        vpHomeMain.adapter = homeFragmentStateAdapter

        tabLayout.addOnTabSelectedListener(this)  // 滑动/点击后进行格式切换

        TabLayoutMediator(tabLayout, vpHomeMain) { tab, position ->
            //设置各项默认视图
            val customView = View.inflate(tabLayout.context, R.layout.custom_view_textview, null)
            val textView = customView.findViewById<TextView>(R.id.tv_custom)
            textView.text = tabTitles[position]
            textView.textSize = 17F
            textView.typeface = DEFAULT
            textView.setTextColor(ContextCompat.getColor(tabLayout.context, R.color.text_gray))
            tab.customView = customView
        }.attach()

        vpHomeMain.currentItem = 2
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

    //tabview视图格式切换
    private fun setTabView(tab: TabLayout.Tab, isSelected: Boolean) {
        val textView = tab.customView?.findViewById<TextView>(R.id.tv_custom)
        if (textView != null) {
            if (isSelected) {
                textView.paint.isFakeBoldText = true    //字体加粗，不能使用会引起指示条抖动的typeface
                textView.setTextColor(ContextCompat.getColor(tabLayout.context, R.color.white))
            } else {
                textView.paint.isFakeBoldText = false
                textView.setTextColor(ContextCompat.getColor(tabLayout.context, R.color.text_gray))
            }
        }
    }

}

